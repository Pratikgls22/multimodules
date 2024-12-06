package com.ecommerce.ecom_essentials.service.impl;

import com.ecommerce.ecom_essentials.entities.RoleEntity;
import com.ecommerce.ecom_essentials.entities.UserDetailsEntity;
import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.entities.UserRoleMappingEntity;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import com.ecommerce.ecom_essentials.exception.CustomException;
import com.ecommerce.ecom_essentials.repository.RoleRepository;
import com.ecommerce.ecom_essentials.repository.UserDetailsRepository;
import com.ecommerce.ecom_essentials.repository.UserRepository;
import com.ecommerce.ecom_essentials.repository.UserRoleMappingRepository;
import com.ecommerce.ecom_essentials.requestDto.UpdateUserRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UserRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.UserProjection;
import com.ecommerce.ecom_essentials.responseDto.UserResponseDTO;
import com.ecommerce.ecom_essentials.service.UserService;
import com.ecommerce.ecom_essentials.utility.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Utilities utilities;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;


    // Method For Check User Exist or Not :
    private UserEntity existenceOfUser(UserRequestDTO userRequestDTO) {
        var user = this.userRepository.findByEmail(userRequestDTO.getEmail());
        if (user.isPresent()) {
            log.info("User is already exist !");
            throw new CustomException(ExceptionEnum.USER_EXISTS.getValue(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    // Method For create and Save User Info :
    private UserEntity createAndSaveUser(UserRequestDTO userRequestDTO, UserEntity currentUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequestDTO.getUserName());
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setCreatedBy(currentUser);
        userEntity.setUpdatedBy(currentUser);
        return userEntity;
    }

    // Method For Find Or create User Role :
    private RoleEntity findOrCreateRole(UserRequestDTO userRequestDTO, UserEntity currentUser) {
        RoleEntity roleEntity = roleRepository.findByRoleName(userRequestDTO.getRoleName())
                .orElseGet(() -> {
                    RoleEntity newRole = new RoleEntity();
                    newRole.setRoleName(userRequestDTO.getRoleName());
                    newRole.setCreatedBy(currentUser);
                    newRole.setUpdatedBy(currentUser);
                    RoleEntity saveNewRole = this.roleRepository.save(newRole);
                    return saveNewRole;
                });
        return roleEntity;
    }

    // Method For Set Role and User Into UserRoleMapping :
    private UserRoleMappingEntity saveUserAndRoleEntity(UserEntity saveUserEntity, RoleEntity roleEntity, UserEntity currentUser) {
        UserRoleMappingEntity userRoleMappingEntity = new UserRoleMappingEntity();
        userRoleMappingEntity.setRoleId(roleEntity);
        userRoleMappingEntity.setUserId(saveUserEntity);
        userRoleMappingEntity.setCreatedBy(currentUser);
        userRoleMappingEntity.setUpdatedBy(currentUser);
        return userRoleMappingEntity;
    }

    // Method For User Exist Or Not Help of UserId :
    private UserEntity checkUserExistOrNotById(Long id) {
        var user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new CustomException(ExceptionEnum.USER_NAME_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }

    // Method For User Exist or not in UserDetails :
    private UserDetailsEntity checkUserExistOrNotInUserDeatils(UserEntity userEntity) {
        var user = this.userDetailsRepository.findByUserId(userEntity);
        if (user.isEmpty()) {
            throw new CustomException(ExceptionEnum.USER_ID_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }

    // Method For User Exist or not in UserRoleMapping :
    private UserRoleMappingEntity checkUserExistOrNotInUserRoleMappig(UserEntity userEntity) {
        var user = this.userRoleMappingRepository.findByUserId(userEntity);
        if (user.isEmpty()) {
            throw new CustomException(ExceptionEnum.USER_ID_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }

    private void setStatus(UserEntity userEntity, UserDetailsEntity userDetailsEntity, UserRoleMappingEntity userRoleMappingEntity, Boolean isActive, Boolean isDelete) {
        userEntity.setActive(isActive);
        userEntity.setDelete(isDelete);
        userDetailsEntity.setActive(isActive);
        userDetailsEntity.setDelete(isDelete);
        userRoleMappingEntity.setActive(isActive);
        userRoleMappingEntity.setDelete(isDelete);
        this.userRepository.save(userEntity);
        this.userDetailsRepository.save(userDetailsEntity);
        this.userRoleMappingRepository.save(userRoleMappingEntity);
    }


    @Override
    public void createUser(UserRequestDTO userRequestDTO) {
        try {
            // Checking User is Exist or not : for that using like * Composite Unique Condition *
            UserEntity user = existenceOfUser(userRequestDTO);
            log.info("User is ::: {}", user);

            // Fetch CurrentUser :
            UserEntity currentUser = utilities.currentUser();
            System.out.println("currentUser in UserServiceImpl class= " + currentUser);

            // Use Helper Method to Create and Save the User :
            UserEntity userEntity = createAndSaveUser(userRequestDTO, currentUser);
            UserEntity saveUserEntity = this.userRepository.save(userEntity);
            log.info("User Saved :::: {}", saveUserEntity);

            // Use Helper Method to Create and Save the UserDetails :
            UserDetailsEntity userDetailsEntity = getUserDetailsEntity(userRequestDTO, saveUserEntity, currentUser);
            UserDetailsEntity details = this.userDetailsRepository.save(userDetailsEntity);
            log.info("User Details Saved :::: {}", details);

            // Use Helper Method to Find Or Create User Role :
            RoleEntity roleEntity = findOrCreateRole(userRequestDTO, currentUser);
            log.info("Role is :::: {}", roleEntity.getRoleName(), roleEntity.getId());

            // Use Helper Method to Set User and Role Entities :
            UserRoleMappingEntity userRoleMappingEntity = saveUserAndRoleEntity(saveUserEntity, roleEntity, currentUser);
            UserRoleMappingEntity mappingEntity = this.userRoleMappingRepository.save(userRoleMappingEntity);
            log.info("User Role Mapping saved :::: {}", mappingEntity);

        } catch (CustomException e) {
            log.info("Exception Catch at Adding User At Database in UserServiceImpl::::{}", HttpStatus.BAD_REQUEST);
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }

    }

    @Override
    public List<UserProjection> searchAllUsers(Pageable pageable, String searchKey) {
        try {
            // For Find All Users Details :
            var user = this.userRepository.findAllUserDetails(pageable, searchKey);
            if (user.isEmpty()) {
                throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
            return user;
        } catch (CustomException e) {
            log.info("Exception catch in get All user Details in user service imp");
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            // Check User is existed or Not In User Master:
            UserEntity userEntity = checkUserExistOrNotById(id);
            // Check User Id is existed or Not In UserDetails:
            UserDetailsEntity userDetailsEntity = checkUserExistOrNotInUserDeatils(userEntity);
            // Check User ID is existed or Not In UserRoleMapping:
            UserRoleMappingEntity userRoleMappingEntity = checkUserExistOrNotInUserRoleMappig(userEntity);

            // Set User Status for Deactivate so User mark as deleted:
            setStatus(userEntity, userDetailsEntity, userRoleMappingEntity, false, true);

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void changeStatus(Long id, Boolean status) {
        try {
            // Check User is existed or Not In User Master:
            UserEntity userEntity = checkUserExistOrNotById(id);
            // Check User ID is existed or Not In UserDetails:
            UserDetailsEntity userDetailsEntity = checkUserExistOrNotInUserDeatils(userEntity);
            // Check User ID is existed or Not In UserRoleMapping:
            UserRoleMappingEntity userRoleMappingEntity = checkUserExistOrNotInUserRoleMappig(userEntity);

            if (!status) {
                // Deactivate the user and mark them as deleted:
                setStatus(userEntity, userDetailsEntity, userRoleMappingEntity, false, true);
            }
            if (status) {
                // Activate the user and set them as not deleted:
                setStatus(userEntity, userDetailsEntity, userRoleMappingEntity, true, false);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public UserResponseDTO updateUserById(Long id, UpdateUserRequestDTO updateUserRequestDTO) {
        var user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
        } else {
            UserEntity currentUser = utilities.currentUser();

            UserEntity userEntity = user.get();
            userEntity.setUserName(updateUserRequestDTO.getUserName());
            userEntity.setEmail(updateUserRequestDTO.getEmail());
            userEntity.setCreatedBy(currentUser);
            userEntity.setUpdatedBy(currentUser);
            UserEntity updatedUserEntity = this.userRepository.save(userEntity);
            log.info("User Updated !! :::: {}", updatedUserEntity);

            UserDetailsEntity userDetailsEntity = this.userDetailsRepository.findByUserId(userEntity)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND));
            userDetailsEntity.setPhoneNumber(updateUserRequestDTO.getPhoneNumber());
            userDetailsEntity.setAlternatePhoneNumber(updateUserRequestDTO.getAlternatePhoneNumber());
            userDetailsEntity.setAddress(updateUserRequestDTO.getAddress());
            userDetailsEntity.setCity(updateUserRequestDTO.getCity());
            userDetailsEntity.setState(updateUserRequestDTO.getState());
            userDetailsEntity.setPincode(updateUserRequestDTO.getPinCode());
            userDetailsEntity.setCountry(updateUserRequestDTO.getCountry());
            userDetailsEntity.setAccountNumber(updateUserRequestDTO.getAccountNumber());
            userDetailsEntity.setAccountHolderName(updateUserRequestDTO.getAccountHolderName());
            userDetailsEntity.setIfscCode(updateUserRequestDTO.getIfscCode());
            userDetailsEntity.setPanNumber(updateUserRequestDTO.getPanNumber());
            userDetailsEntity.setGstNumber(updateUserRequestDTO.getGstNumber());
            this.userDetailsRepository.save(userDetailsEntity);

            var roleEntity = this.roleRepository.findByRoleName(updateUserRequestDTO.getRoleName());
            RoleEntity setRole = roleEntity.get();
            log.info("SetRole as :: {}", setRole);

            UserRoleMappingEntity userRoleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND));
            userRoleMappingEntity.setRoleId(setRole);
            userRoleMappingEntity.setUserId(userEntity);
            userRoleMappingEntity.setCreatedBy(currentUser);
            userRoleMappingEntity.setUpdatedBy(currentUser);
            this.userRoleMappingRepository.save(userRoleMappingEntity);

            return new UserResponseDTO();
        }
    }

    private UserResponseDTO getUserDetailsById(UserEntity currentUser, UserDetailsEntity currenrtUserDetails) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(currentUser.getId());
        userResponseDTO.setUserName(currentUser.getUserName());
        userResponseDTO.setEmail(currentUser.getEmail());
        userResponseDTO.setPhoneNumber(currenrtUserDetails.getPhoneNumber());
        userResponseDTO.setAlternatePhoneNumber(currenrtUserDetails.getAlternatePhoneNumber());
        userResponseDTO.setAddress(currenrtUserDetails.getAddress());
        userResponseDTO.setCity(currenrtUserDetails.getCity());
        userResponseDTO.setState(currenrtUserDetails.getState());
        userResponseDTO.setPinCode(currenrtUserDetails.getPincode());
        userResponseDTO.setCountry(currenrtUserDetails.getCountry());
        userResponseDTO.setAccountHolderName(currenrtUserDetails.getAccountHolderName());
        userResponseDTO.setIfscCode(currenrtUserDetails.getIfscCode());
        userResponseDTO.setPanNumber(currenrtUserDetails.getPanNumber());
        userResponseDTO.setGstNumber(currenrtUserDetails.getGstNumber());
        return userResponseDTO;
    }

    // Method For create and Save UserDetails :
    private UserDetailsEntity getUserDetailsEntity(UserRequestDTO userRequestDTO, UserEntity
            saveUserEntity, UserEntity currentUser) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setPhoneNumber(userRequestDTO.getPhoneNumber());
        userDetailsEntity.setAlternatePhoneNumber(userRequestDTO.getAlternatePhoneNumber());
        userDetailsEntity.setAddress(userRequestDTO.getAddress());
        userDetailsEntity.setCity(userRequestDTO.getCity());
        userDetailsEntity.setState(userRequestDTO.getState());
        userDetailsEntity.setPincode(userRequestDTO.getPinCode());
        userDetailsEntity.setCountry(userRequestDTO.getCountry());
        userDetailsEntity.setAccountNumber(userRequestDTO.getAccountNumber());
        userDetailsEntity.setAccountHolderName(userRequestDTO.getAccountHolderName());
        userDetailsEntity.setIfscCode(userRequestDTO.getIfscCode());
        userDetailsEntity.setPanNumber(userRequestDTO.getPanNumber());
        userDetailsEntity.setGstNumber(userRequestDTO.getGstNumber());
        userDetailsEntity.setUserId(saveUserEntity);
        userDetailsEntity.setCreatedBy(currentUser);
        userDetailsEntity.setUpdatedBy(currentUser);
        return userDetailsEntity;
    }

}

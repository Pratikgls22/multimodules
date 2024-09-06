package com.ecommerce.demo.service.impl;


import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.utill.Utilities;
import com.ecommerce.entity.entity.RoleEntity;
import com.ecommerce.entity.entity.UserDetailsEntity;
import com.ecommerce.entity.entity.UserEntity;
import com.ecommerce.entity.entity.UserRoleMappingEntity;
import com.ecommerce.entity.projection.GetAllUserDetails;
import com.ecommerce.entity.requestDto.RegisterUserDTO;
import com.ecommerce.entity.requestDto.UpdateUserDTO;
import com.ecommerce.entity.responseDto.UserResponseDTO;
import com.ecommerce.repository.repository.RoleRepository;
import com.ecommerce.repository.repository.UserDetailsRepository;
import com.ecommerce.repository.repository.UserRepository;
import com.ecommerce.repository.repository.UserRoleMappingRepository;
import com.ecommerce.utility.enums.ExceptionEnum;
import com.ecommerce.utility.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Utilities utilities;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(RegisterUserDTO registerUserDTO) {
        try {
//      checking user is already exist
            var user = this.userRepository.findByEmail(registerUserDTO.getEmail());
            if (user.isPresent()) {
                log.info("User Already Exist::");
                throw new CustomException("User Already Exist", HttpStatus.NOT_ACCEPTABLE);
            }
//      fetch current user

            UserEntity currentUser = utilities.currentUser();

            UserEntity entity = new UserEntity();
            entity.setEmail(registerUserDTO.getEmail());
            entity.setUserName(registerUserDTO.getUserName());
            entity.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            entity.setCreatedBy(currentUser);
            entity.setUpdatedBy(currentUser);

            UserEntity saveUserEntity = this.userRepository.save(entity);
            log.info("User Saved :::::::::::::::::::::{}", saveUserEntity.getId());

            UserDetailsEntity userDetailsEntity = getUserDetailsEntity(registerUserDTO, saveUserEntity, currentUser);
            this.userDetailsRepository.save(userDetailsEntity);

            RoleEntity roleEntity = roleRepository.findById(registerUserDTO.getRolesId().longValue()).orElseThrow(() -> new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST));

            UserRoleMappingEntity userRoleMappingEntity = new UserRoleMappingEntity();
            userRoleMappingEntity.setRoleId(roleEntity);
            userRoleMappingEntity.setUserId(saveUserEntity);
            userRoleMappingEntity.setCreatedBy(currentUser);
            userRoleMappingEntity.setUpdatedBy(currentUser);
            this.userRoleMappingRepository.save(userRoleMappingEntity);

        } catch (CustomException e) {
            log.info("Exception Catch at Adding User At Database in UserServiceImpl::::{}", HttpStatus.BAD_REQUEST);
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public Page<GetAllUserDetails> getAll(Pageable pageable, String searchKey) {
        try {
            return this.userRepository.findAllUserDetail(pageable, searchKey);
        } catch (CustomException e) {
            log.info("Exception catch in get All user Details in user service imp");
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        try {
            var optionalUser = this.userRepository.findById(id);

            UserEntity currentUser = optionalUser.get();

            var userDetails = this.userDetailsRepository.findById(currentUser.getId());

            UserDetailsEntity currentUserDetails = userDetails.get();

            return getUserResponseDTO(currentUser, currentUserDetails);

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(Long id, Boolean status) {
        try {

            UserEntity userEntity = this.userRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST));

            UserDetailsEntity userDetailsEntity = this.userDetailsRepository.findByUserId(userEntity)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.USER_DETAILS_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST));

            UserRoleMappingEntity roleMappingEntity = this.userRoleMappingRepository.findByUserId(userEntity)
                    .orElseThrow(() -> new CustomException(ExceptionEnum.USER_ROLE_MAPPING_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST));

            if (status) {
                userEntity.setActive(false);
                userDetailsEntity.setActive(false);
                roleMappingEntity.setActive(false);
                this.userRepository.save(userEntity);
                this.userDetailsRepository.save(userDetailsEntity);
                this.userRoleMappingRepository.save(roleMappingEntity);
            }
            if (!status) {
                userEntity.setActive(true);
                userDetailsEntity.setActive(true);
                roleMappingEntity.setActive(true);
                this.userRepository.save(userEntity);
                this.userDetailsRepository.save(userDetailsEntity);
                this.userRoleMappingRepository.save(roleMappingEntity);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long id, UpdateUserDTO updateUserDTO) {
        try {
//      checking user is present or not
            var user = this.userRepository.findById(id);
            if (user.isEmpty()) {
                throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
//      fetch current user
            UserEntity currentUser = utilities.currentUser();

            UserEntity entity = user.get();
            entity.setUserName(updateUserDTO.getUserName());
            entity.setCreatedBy(currentUser);
            entity.setUpdatedBy(currentUser);

            var saveUserEntity = this.userRepository.save(entity);

            var userDetail = this.userDetailsRepository.findById(saveUserEntity.getId());

            UserDetailsEntity userDetailsEntity = userDetail.get();

            userDetailsEntity.setUserId(saveUserEntity);
            userDetailsEntity.setGender(updateUserDTO.getGender());
            userDetailsEntity.setLastName(updateUserDTO.getLastName());
            userDetailsEntity.setFirstName(updateUserDTO.getFirstName());
            userDetailsEntity.setDob(updateUserDTO.getDob());
            userDetailsEntity.setAddress(updateUserDTO.getAddress());
            userDetailsEntity.setCreatedBy(currentUser);
            userDetailsEntity.setUpdatedBy(currentUser);
            this.userDetailsRepository.save(userDetailsEntity);

//      getting new role from request
            var roleEntity = this.roleRepository.findById(updateUserDTO.getRolesId());
            RoleEntity role = roleEntity.get();

//      getting role from database
            Optional<UserRoleMappingEntity> userRole = this.userRoleMappingRepository.findByUserId(saveUserEntity);

//      updating role
            if (userRole.isPresent()) {
                UserRoleMappingEntity userRoleMappingEntity = userRole.get();

                userRoleMappingEntity.setRoleId(role);
                userRoleMappingEntity.setUserId(saveUserEntity);
                userRoleMappingEntity.setCreatedBy(currentUser);
                userRoleMappingEntity.setUpdatedBy(currentUser);
                this.userRoleMappingRepository.save(userRoleMappingEntity);
            }

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {

        try {
//    checking user is present
            var user = this.userRepository.findById(id);

            if (user.isEmpty()) {
                throw new CustomException(ExceptionEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                UserEntity entity = user.get();
                UserDetailsEntity userDetailsEntity = this.userDetailsRepository.findById(entity.getId()).orElseThrow(() -> new CustomException(ExceptionEnum.USER_NAME_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
                UserRoleMappingEntity userRoleMappingEntity = this.userRoleMappingRepository.findById(entity.getId()).orElseThrow(() -> new CustomException(ExceptionEnum.USER_NAME_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
                entity.setActive(false);
                entity.setDelete(true);
                userDetailsEntity.setActive(false);
                userDetailsEntity.setDelete(true);
                userRoleMappingEntity.setActive(false);
                userRoleMappingEntity.setDelete(true);
                this.userRepository.save(entity);
                this.userDetailsRepository.save(userDetailsEntity);
                this.userRoleMappingRepository.save(userRoleMappingEntity);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }

    }

    @Override
    public void m2() {
        System.out.println("primary logic");
    }


    private static UserDetailsEntity getUserDetailsEntity(RegisterUserDTO registerUserDTO, UserEntity saveUserEntity, UserEntity currentUser) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setDob(registerUserDTO.getDob());
        userDetailsEntity.setAddress(registerUserDTO.getAddress());
        userDetailsEntity.setFirstName(registerUserDTO.getFirstName());
        userDetailsEntity.setLastName(registerUserDTO.getLastName());
        userDetailsEntity.setGender(registerUserDTO.getGender());
        userDetailsEntity.setUserId(saveUserEntity);
        userDetailsEntity.setCreatedBy(currentUser);
        userDetailsEntity.setUpdatedBy(currentUser);
        return userDetailsEntity;
    }

    private static UserResponseDTO getUserResponseDTO(UserEntity currentUser, UserDetailsEntity currentUserDetails) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(currentUser.getId());
        userResponseDTO.setEmail(currentUser.getEmail());
        userResponseDTO.setUserName(currentUser.getUserName());
        userResponseDTO.setAddress(currentUserDetails.getAddress());
        userResponseDTO.setDateOfBirth(currentUserDetails.getDob());
        userResponseDTO.setFirstName(currentUserDetails.getFirstName());
        userResponseDTO.setLastName(currentUserDetails.getLastName());
        userResponseDTO.setGender(currentUserDetails.getGender());
        userResponseDTO.setIsActive(currentUser.isActive());
        return userResponseDTO;
    }
}

package com.ecommerce.services.service.impl;

import com.ecommerce.model.dto.UpdateUserDto;
import com.ecommerce.model.dto.UserProjection;
import com.ecommerce.model.dto.UserRequestDto;
import com.ecommerce.model.entity.RoleEntity;
import com.ecommerce.model.entity.UserEntity;
import com.ecommerce.model.entity.UserRoleEntity;
import com.ecommerce.utility.exception.CustomException;
import com.ecommerce.repositry.repositry.RoleRepositry;
import com.ecommerce.repositry.repositry.UserRepositry;
import com.ecommerce.repositry.repositry.UserRoleRepositry;
import com.ecommerce.services.service.UserService;
import com.ecommerce.utility.utill.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepositry userRepositry;
    private final Utilities utilities;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepositry roleRepositry;
    private final UserRoleRepositry userRoleRepositry;

    private static final String ACTION_1 = "User Not Found !!";

    @Override
    public void addUser(UserRequestDto userRequestDto) {

        //Checking user is exist or not
        var user = this.userRepositry.findByUserName(userRequestDto.getUserName());
        if (user.isPresent()){
            log.info("User Already Exist");
            throw new CustomException("User Already Exist", HttpStatus.NOT_ACCEPTABLE);
        }
        // Fetch Current User
        UserEntity currentUser = utilities.getCurrentUSer();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequestDto.getUserName());
        userEntity.setUserEmail(userRequestDto.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(userRequestDto.getUserPassword()));
        userEntity.setCreatedBy(currentUser);
        userEntity.setUpdatedBy(currentUser);
        UserEntity userData = this.userRepositry.save(userEntity);

        RoleEntity roleEntity = roleRepositry.findById(userRequestDto.getRoleId().longValue())
                .orElseThrow(() -> new CustomException("Role Id Not Found",HttpStatus.BAD_REQUEST));

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userData);
        userRoleEntity.setRoles(roleEntity);
        userRoleEntity.setCreatedBy(currentUser);
        userRoleEntity.setUpdatedBy(currentUser);
        this.userRoleRepositry.save(userRoleEntity);

    }

    @Override
    public List<UserProjection> getAllUser() {
        return userRepositry.findAllUserDetails();
    }

    @Override
    public Object searchById(Long id) {
        try {
            Optional<UserEntity> user = this.userRepositry.findById(id);
            if (user.isEmpty()){
                throw new CustomException(ACTION_1,HttpStatus.NOT_FOUND);
            }
            return user;
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(),e.getHttpStatus());
        }
    }

    @Override
    public Object updateUser(UpdateUserDto updateUserDto, Long id) {
        var optionalUser = this.userRepositry.findById(id);
        UserEntity user = optionalUser.get();

        //Fetch Current User
        UserEntity currentUser = utilities.getCurrentUSer();
        UserEntity userEntity = currentUser;
        userEntity.setUserName(updateUserDto.getUserName());
        userEntity.setUpdatedBy(currentUser);
        var saveUserEntity = userRepositry.save(userEntity);

        var optionalUserRole = this.userRoleRepositry.findByUser(user);
        if (optionalUserRole.isPresent()){
            UserRoleEntity userRoleEntity = optionalUserRole.get();

            var optionalRole = this.roleRepositry.findById(updateUserDto.getRoleId());
            if (optionalRole.isPresent()){
                userRoleEntity.setRoles(optionalRole.get());
                this.userRoleRepositry.save(userRoleEntity);
            }
            else {
                throw new CustomException("Role Not Found :",HttpStatus.NOT_FOUND);
            }
        }
        else {
            throw new CustomException("UserRoel not Found : ",HttpStatus.NOT_FOUND);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        var optionalUser = this.userRepositry.findById(id);
        UserEntity user = optionalUser.get();
        user.setIsDeleted(Boolean.TRUE);
        user.setIsActive(Boolean.FALSE);
        this.userRepositry.save(user);

        var optionalUserRole = this.userRoleRepositry.findByUser(user);
        UserRoleEntity userRole = optionalUserRole.get();
        userRole.setIsDeleted(Boolean.TRUE);
        userRole.setIsActive(Boolean.FALSE);
        this.userRoleRepositry.save(userRole);
    }

    @Override
    public void changeStatus(Long id) {
        var optionalUser = this.userRepositry.findById(id);
        UserEntity user = optionalUser.get();
        user.setIsActive(Boolean.TRUE);
        user.setIsDeleted(Boolean.FALSE);
        userRepositry.save(user);

        var optionalUserRole = this.userRoleRepositry.findByUser(user);
        UserRoleEntity userRole = optionalUserRole.get();
        userRole.setIsActive(Boolean.TRUE);
        userRole.setIsDeleted(Boolean.FALSE);
        this.userRoleRepositry.save(userRole);
    }


}

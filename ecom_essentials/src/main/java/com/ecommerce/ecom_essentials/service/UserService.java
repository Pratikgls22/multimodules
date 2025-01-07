package com.ecommerce.ecom_essentials.service;

import com.ecommerce.ecom_essentials.entities.UserDetailsEntity;
import com.ecommerce.ecom_essentials.requestDto.UpdateUserRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UserRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.VendorRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.UserProjection;
import com.ecommerce.ecom_essentials.responseDto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void createUser(UserRequestDTO userRequestDTO);

    List<UserProjection> searchAllUsers(Pageable pageable, String searchKey);

    void deleteUserById(Long id);

    void changeStatus(Long id, Boolean status);

    UserResponseDTO updateUserById(Long id, UpdateUserRequestDTO updateUserRequestDTO);

    UserDetailsEntity createVendor(@Valid VendorRequestDTO vendorRequestDTO);
}

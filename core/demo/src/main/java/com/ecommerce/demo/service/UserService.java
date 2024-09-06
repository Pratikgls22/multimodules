package com.ecommerce.demo.service;


import com.ecommerce.entity.projection.GetAllUserDetails;
import com.ecommerce.entity.requestDto.RegisterUserDTO;
import com.ecommerce.entity.requestDto.UpdateUserDTO;
import com.ecommerce.entity.responseDto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  void registerUser(RegisterUserDTO registerUserDTO);

  Page<GetAllUserDetails> getAll(Pageable pageable, String searchKey);

  UserResponseDTO getUserById(Long id);

  void changeStatus(Long id, Boolean status);

  void updateUser(Long id, UpdateUserDTO updateUserDTO);

  void deleteUser(Long id);

  void m2();
}

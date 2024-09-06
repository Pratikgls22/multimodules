package com.ecommerce.services.service;

import com.ecommerce.model.dto.UpdateUserDto;
import com.ecommerce.model.dto.UserProjection;
import com.ecommerce.model.dto.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void addUser(UserRequestDto userRequestDto);

    List<UserProjection> getAllUser();

    Object searchById(Long id);

    Object updateUser(UpdateUserDto updateUserDto, Long id);

    void deleteUser(Long id);

    void changeStatus(Long id);
}

package com.ecommerce.domain.controller;

import com.ecommerce.model.dto.ApiResponse;
import com.ecommerce.model.dto.UpdateUserDto;
import com.ecommerce.model.dto.UserRequestDto;
import com.ecommerce.services.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/adduser")
    public ResponseEntity<ApiResponse> addUser(@Valid  @RequestBody UserRequestDto userRequestDto){
        this.userService.addUser(userRequestDto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User ADDed",HttpStatus.OK),HttpStatus.OK);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllUser(){
        var response = this.userService.getAllUser();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"All User List",response),HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        var response = this.userService.searchById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Found with id : "+id,response),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable Long id){
        var response = this.userService.updateUser(updateUserDto,id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User update successfully",response),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User deleted successfuly",HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/changestatus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> changeStatus(@PathVariable Long id){
        this.userService.changeStatus(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Status changed successfuly",HttpStatus.OK),HttpStatus.OK);
    }
}

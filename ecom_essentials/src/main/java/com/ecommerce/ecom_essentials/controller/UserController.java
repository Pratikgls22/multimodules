package com.ecommerce.ecom_essentials.controller;

import com.ecommerce.ecom_essentials.enums.GetSortBy;
import com.ecommerce.ecom_essentials.requestDto.UpdateUserRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.UserRequestDTO;
import com.ecommerce.ecom_essentials.requestDto.VendorRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.ApiResponse;
import com.ecommerce.ecom_essentials.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        this.userService.createUser(userRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED, "User Created Successfully !", HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @GetMapping("/searchUser")
    public ResponseEntity<ApiResponse> searchUser(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "Id", required = false) GetSortBy getSortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC", required = false) Sort.Direction sortOrder,
            @RequestParam(value = "searchKey", defaultValue = "", required = false) String searchKey)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder, getSortBy.getValue()));
        var response = this.userService.searchAllUsers(pageable, searchKey);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "List Of User !!", response), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable(value = "id") Long id){
        this.userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Deleted Successfully",HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{id}/{status}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> changeStatus(@PathVariable(value = "id") Long id, @PathVariable(value = "status") Boolean status){
        System.out.println("id in UserController= " + id);
        this.userService.changeStatus(id,status);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Change User Status Successfully",HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/updateUserById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateUserById(@Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO, @PathVariable(value = "id") Long id){
        var response = this.userService.updateUserById(id,updateUserRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"User Updated Successfully",response),HttpStatus.OK);
    }

    @PostMapping("/createVendor")
    @PreAuthorize("hasAuthority('Customer')")
    public ResponseEntity<ApiResponse> createVendor(@RequestBody @Valid VendorRequestDTO vendorRequestDTO) {
        var response = this.userService.createVendor(vendorRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED, "User Created Successfully !", response), HttpStatus.CREATED);
    }
}

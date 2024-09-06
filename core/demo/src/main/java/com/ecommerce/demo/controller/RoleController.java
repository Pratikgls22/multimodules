package com.ecommerce.demo.controller;


import com.ecommerce.demo.service.RoleService;
import com.ecommerce.entity.requestDto.RoleRequestDTO;
import com.ecommerce.entity.responseDto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addNewRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        this.roleService.addRole(roleRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Added Successfully", new HashMap<>()), HttpStatus.OK);
    }

    @GetMapping("/getAllRole")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllRole() {
        var response = this.roleService.getAllRole();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Found Successfully", response), HttpStatus.OK);
    }

    @GetMapping("/getRoleById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable(value = "id") Long id) {
        var response = this.roleService.getById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Found Successfully", response), HttpStatus.OK);
    }

    @PutMapping("/updateRole/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable(value = "id") Long id, @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        this.roleService.updateRole(id, roleRequestDTO);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Updated Successfully", new HashMap<>()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable(value = "id") Long id) {
        this.roleService.deleteRole(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Deleted Successfully", new HashMap<>()), HttpStatus.OK);
    }

    @PatchMapping("/changeStatus/{id}/{status}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> changeStatus(@PathVariable(value = "id") Long id, @PathVariable(value = "status") Boolean status) {
        this.roleService.changeStatus(id, status);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Role Changed Successfully", new HashMap<>()), HttpStatus.OK);
    }
}

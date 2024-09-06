package com.ecommerce.demo.service;


import com.ecommerce.entity.requestDto.RoleRequestDTO;
import com.ecommerce.entity.responseDto.RoleResponseDTO;

import java.util.List;

public interface RoleService {
  void addRole(RoleRequestDTO roleRequestDTO);

  List<RoleResponseDTO> getAllRole();

  RoleResponseDTO getById(Long id);

  void updateRole(Long id, RoleRequestDTO roleRequestDTO);

  void deleteRole(Long id);

  void changeStatus(Long id, Boolean status);
}

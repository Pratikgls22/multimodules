package com.ecommerce.ecom_essentials.service;


import com.ecommerce.ecom_essentials.requestDto.RoleRequestDTO;
import com.ecommerce.ecom_essentials.responseDto.RoleResponseDTO;

import java.util.List;

public interface RoleService {
  void addRole(RoleRequestDTO roleRequestDTO);

//  List<RoleResponseDTO> getAllRole();

  RoleResponseDTO getById(String name);

  void updateRole(Long id, RoleRequestDTO roleRequestDTO);

  void deleteRole(Long id);

  void changeStatus(Long id, Boolean status);
}

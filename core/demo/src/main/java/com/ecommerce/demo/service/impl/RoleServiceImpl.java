package com.ecommerce.demo.service.impl;

import com.ecommerce.demo.service.RoleService;
import com.ecommerce.demo.utill.Utilities;
import com.ecommerce.entity.entity.RoleEntity;
import com.ecommerce.entity.entity.UserEntity;
import com.ecommerce.entity.requestDto.RoleRequestDTO;
import com.ecommerce.entity.responseDto.RoleResponseDTO;
import com.ecommerce.repository.repository.RoleRepository;
import com.ecommerce.security.config.ModalMapperConfig;
import com.ecommerce.utility.enums.ExceptionEnum;
import com.ecommerce.utility.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Utilities utilities;
    private final ModalMapperConfig modalMapperConfig;

    private RoleResponseDTO mapEntityToDto(RoleEntity roleEntity) {
        return modalMapperConfig.modelMapper().map(roleEntity, RoleResponseDTO.class);
    }

    @Override
    public void addRole(RoleRequestDTO roleRequestDTO) {
        try {
            var user = this.roleRepository.findByRole(roleRequestDTO.getRoleName());

            if (user.isPresent()) {
                throw new CustomException(ExceptionEnum.ROLE_ALREADY_EXIST.getMessage(), HttpStatus.BAD_REQUEST);
            }

            UserEntity currentUser = utilities.currentUser();

            System.out.println("currentUser ================ " + currentUser);
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setCreatedBy(currentUser);
            roleEntity.setUpdatedBy(currentUser);
            roleEntity.setRole(roleRequestDTO.getRoleName());

            log.info("Role Saved::::{}", roleEntity);

            this.roleRepository.save(roleEntity);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public List<RoleResponseDTO> getAllRole() {
        try {
            return this.roleRepository.findAllActiveRole().stream().map(this::mapEntityToDto).collect(Collectors.toList());
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }

    }

    @Override
    public RoleResponseDTO getById(Long id) {
        try {
            var role = this.roleRepository.findById(id);
            if (role.isPresent()) {
                return this.modalMapperConfig.modelMapper().map(role, RoleResponseDTO.class);
            } else {
                throw new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void updateRole(Long id, RoleRequestDTO roleRequestDTO) {
        try {
            var role = this.roleRepository.findById(id);
            UserEntity currentUser = utilities.currentUser();
            if (role.isPresent()) {
                RoleEntity currentRole = role.get();
                currentRole.setRole(roleRequestDTO.getRoleName());
                currentRole.setCreatedBy(currentUser);
                currentRole.setUpdatedBy(currentUser);
                this.roleRepository.save(currentRole);
            } else {
                throw new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }

        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void deleteRole(Long id) {
        try {
            var user = this.roleRepository.findById(id);
            if (user.isPresent()) {
                RoleEntity role = user.get();
                role.setActive(false);
                role.setDelete(true);
                this.roleRepository.save(role);
            } else {
                throw new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void changeStatus(Long id, Boolean status) {
        try {
            var user = this.roleRepository.findById(id);
            if (user.isPresent()) {
                RoleEntity role = user.get();
                if (status) {
                    role.setActive(false);
                    log.info("Role Changed::::::::::::");
                    this.roleRepository.save(role);
                } else {
                    role.setActive(true);
                    log.info("Role Changed::::::::::::");
                    this.roleRepository.save(role);
                }
            } else {
                throw new CustomException(ExceptionEnum.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getHttpStatus());
        }
    }
}

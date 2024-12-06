package com.ecommerce.ecom_essentials.repository;

import com.ecommerce.ecom_essentials.entities.UserEntity;
import com.ecommerce.ecom_essentials.responseDto.UserProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String username);
    Optional<UserEntity> findByEmailAndUserName(String username,String email);

    @Query(nativeQuery = true, value = "SELECT " +
            "um.id AS id, " +
            "um.is_active AS status, " +
            "um.user_name AS userName, " +
            "um.email AS email, " +
            "rm.role_name AS roleName, " +
            "udm.phone_number AS phoneNumber, " +
            "udm.address AS address, " +
            "udm.city AS city, " +
            "udm.state AS state, " +
            "udm.pin_code AS PINCode, " +
            "udm.country AS country, " +
            "udm.account_holder_name AS accountHolderName " +
            "FROM " +
            "ecom_essentials.public.user_master um " +
            "JOIN " +
            "ecom_essentials.public.user_role_mapping urm ON um.id = urm.user_id " +
            "JOIN " +
            "ecom_essentials.public.role_master rm ON urm.role_id = rm.id " +
            "JOIN " +
            "ecom_essentials.public.user_details_master udm ON um.id = udm.user_id " +
            "WHERE " +
            "urm.is_delete = FALSE " +
            "AND urm.is_active = TRUE " + // Condition for active users
            "AND ( " +
            "um.user_name ILIKE CONCAT('%', :searchKey, '%') " + // Searching first name
            "OR um.email ILIKE CONCAT('%', :searchKey, '%') " + // Searching email
            "OR rm.role_name ILIKE CONCAT('%', :searchKey, '%') " + // Searching RoleName
            ") " +
            "GROUP BY " +
            "um.id, um.is_active, um.user_name, um.email, rm.role_name, udm.phone_number, udm.address, udm.city, udm.state, udm.pin_code, udm.country, udm.account_holder_name " +
            "ORDER BY um.id")
    List<UserProjection> findAllUserDetails(Pageable pageable, String searchKey);

    Optional<UserEntity> findByUserName(String name);
}

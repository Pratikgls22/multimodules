package com.ecommerce.repositry.repositry;

import com.ecommerce.model.dto.UserProjection;
import com.ecommerce.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositry extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUserName(String username);

    Optional<UserEntity> findByUserEmail(String useremail);

    @Query(value = "SELECT ut.id as id, ut.user_name as userName, ut.user_email as userEmail, ut.is_active as isActive , ut.is_delete as isDelete,\n" +
            "re.id as roleId , re.role_name as roleName from  microservice_demo.user_table ut \n" +
            "Join microservice_demo.user_role_table as urt ON ut.id = urt.user_id  \n" +
            "Join microservice_demo.role_entity as re ON urt.role_id = re.id \n" +
            "WHERE ut.is_delete = FALSE \n" +
            "GROUP BY ut.id , ut.user_name ,ut.user_email ,ut.is_active ,re.id ,re.role_name \n"
    ,nativeQuery = true)
    List<UserProjection> findAllUserDetails();
}

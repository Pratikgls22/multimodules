package com.job.females.in.tech.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_master")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "login_attempt")
    private Long loginAttempt;

    @CreatedDate
    @Column(name = "last_login")
    private Date lastLogin;

    @ColumnDefault("false")
    @Column(name = "account_lock")
    private Boolean accountLock;

    @Column(name = "last_modified_password_date")
    private Date lastModifiedPasswordDate;
}

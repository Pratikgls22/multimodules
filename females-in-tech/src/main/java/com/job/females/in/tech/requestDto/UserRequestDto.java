package com.job.females.in.tech.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRequestDto {

    @Size(max = 255, message = "userName should not greater than 255 characters")
    @NotEmpty(message = "userName must not be empty")
    private String userName;

    @Size(max = 255, message = "email should not greater than 255 characters")
    @NotEmpty(message = "email must not be empty")
    private String email;

    private String password;

    private String experienceLevel;

    private String eduction;

    @Past
    private LocalDate dob;


}

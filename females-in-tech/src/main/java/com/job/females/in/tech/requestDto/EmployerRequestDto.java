package com.job.females.in.tech.requestDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployerRequestDto {

    private String companyName;
    private String companyEmail;
    private String companyType;
    private String companySize;
    private String companyPhone;
    private String location;

}

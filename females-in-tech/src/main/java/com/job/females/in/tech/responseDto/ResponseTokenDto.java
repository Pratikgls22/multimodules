package com.job.females.in.tech.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResponseTokenDto {

    private String token;
    private String userRole;
    private Long userId;
    private String userName;

}

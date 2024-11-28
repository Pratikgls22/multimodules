package com.ecommerce.ecom_essentials.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String address;
    private String city;
    private String state;
    private String PINCode;
    private String country;
    private String accountHolderName;
    private String IFSCCode;
    private String PANNumber;
    private String GSTNumber;
}

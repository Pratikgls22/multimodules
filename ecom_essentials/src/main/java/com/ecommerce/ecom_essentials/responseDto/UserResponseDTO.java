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
    private String pinCode;
    private String country;
    private String accountHolderName;
    private String ifscCode;
    private String panNumber;
    private String gstNumber;
}

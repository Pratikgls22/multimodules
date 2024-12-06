package com.ecommerce.ecom_essentials.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestDTO {
    @NotEmpty(message = "UserName is Required")
    @Size(max = 255, message = "UserName should not Be Greater then 255 Characters")
    private String userName;

    @NotEmpty(message = "Email is Required")
    @Size(max = 255, message = "Email should not Be Greater then 255 Characters")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "email is invalid")
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 to 15 digits")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "Phone number must start with a digit between 1-9 and can only contain digits")
    private String phoneNumber;

    private String alternatePhoneNumber;

    @Size(max = 255, message = "address should not Be Greater then 255 Characters")
    private String address;

    @Size(max = 50, message = "city should not Be Greater then 50 Characters")
    private String city;

    @Size(max = 50, message = "state should not Be Greater then 50 Characters")
    private String state;

    @Size(min = 6, max = 6, message = "PIN code must be exactly 6 digits")
    @Pattern(regexp = "^[1-9]\\d{5}$", message = "PIN code must start with 1-9 and be followed by 5 digits")
    private String pinCode;

    @Size(max = 50, message = "Country should not Be Greater then 50 Characters")
    private String country;

    private String accountNumber;

    private String accountHolderName;

    private String ifscCode;

    private String panNumber;

    private String gstNumber;

    @NotEmpty(message = "RoleName Must be Required")
    private String roleName;
}

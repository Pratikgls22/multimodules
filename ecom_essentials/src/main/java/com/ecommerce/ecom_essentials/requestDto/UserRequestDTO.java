package com.ecommerce.ecom_essentials.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @NotEmpty(message = "UserName is Required")
    @Size(max = 255, message = "UserName should not Be Greater then 255 Characters")
    private String userName;

    @NotEmpty(message = "Password is Required")
    @Size(min = 8, message = "Password should not Be less then 8 Characters")
    @Size(max = 15, message = "Password should not Be Greater then 15 Characters")
    private String password;

    @NotEmpty(message = "Email is Required")
    @Size(max = 255, message = "Email should not Be Greater then 255 Characters")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "email is invalid")
    private String email;

    @Pattern(regexp = "^(|[1-9][0-9]*)$", message = "Phone number must start with a digit between 1-9 and can only contain digits")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 to 15 digits")
    private String phoneNumber;

    @Pattern(regexp = "^(|[1-9][0-9]*)$", message = "AlternatePhone number must start with a digit between 1-9 and can only contain digits")
//    @Size(min = 10, max = 15, message = "AlternatePhone number must be between 10 to 15 digits")
    private String alternatePhoneNumber;

    @Size(max = 255, message = "address should not Be Greater then 255 Characters")
    private String address;

    @Size(max = 50, message = "city should not Be Greater then 50 Characters")
    private String city;

    @Size(max = 50, message = "state should not Be Greater then 50 Characters")
    private String state;

    //    @JsonProperty("PINCode")
    private String pinCode;

    @Size(max = 50, message = "Country should not Be Greater then 50 Characters")
    private String country;

//    @Size(min = 8, max = 12, message = "Account number must be between 8 and 12 digits")
    @Pattern(regexp = "^(|[0-9]+)$", message = "Account number must contain only digits")
    private String accountNumber;

//    @Size(min = 1, max = 100, message = "Account holder name must be between 1 and 100 characters")
    private String accountHolderName;

    @Pattern(regexp = "^(|[A-Z]{4}0[A-Z0-9]{6})$", message = "Invalid IFSC Code format")
    private String ifscCode;

    @Pattern(regexp = "^(|[A-Z]{5}[0-9]{4}[A-Z])$", message = "Invalid PAN Number format")
    private String panNumber;

    @Pattern(regexp = "^(|[0-9A-Z]{15})$", message = "Invalid GST Number format")
    private String gstNumber;

    @NotEmpty(message = "RoleName Must be Required")
    private String roleName;
}

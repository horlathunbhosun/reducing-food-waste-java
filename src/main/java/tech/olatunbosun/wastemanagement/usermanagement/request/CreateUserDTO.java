package tech.olatunbosun.wastemanagement.usermanagement.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;
import tech.olatunbosun.wastemanagement.validation.EnumValue;
import tech.olatunbosun.wastemanagement.validation.ValidEmail;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "Full name is required")
    @JsonProperty("fullname")
    private String fullName;

    @ValidEmail
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "User type is required")
    @JsonProperty("user_type")
    @EnumValue(enumClass = UserType.class, message = "User type should be either 'waste_warrior' or 'partner' or 'admin'")
    private String userType;

    @JsonProperty("partner")
    private PartnerDTO partner;

}

package tech.olatunbosun.wastemanagement.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;
import tech.olatunbosun.wastemanagement.validation.EnumValue;
import tech.olatunbosun.wastemanagement.validation.ValidEmail;

/**
 * @author olulodeolatunbosun
 * @created 7/20/24/07/2024 - 10:51 pm
 */

@Data
public class GoogleAuthRequest {


    @NotBlank(message = "Id token is required")
    private String idToken;


    @NotBlank(message = "FullName name is required")
    @JsonProperty("fullname")
    private String fullName;


    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "User type is required")
    @JsonProperty("user_type")
    @EnumValue(enumClass = UserType.class, message = "User type should be either 'waste_warrior' or 'partner' or 'admin'")
    private String userType;





}

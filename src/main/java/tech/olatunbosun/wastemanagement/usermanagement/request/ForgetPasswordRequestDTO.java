package tech.olatunbosun.wastemanagement.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.olatunbosun.wastemanagement.validation.ValidEmail;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequestDTO {

    @ValidEmail
    @JsonProperty("email")
    private String email;

    @Null
    @JsonProperty("phone_number")
    private String phoneNumber;
}

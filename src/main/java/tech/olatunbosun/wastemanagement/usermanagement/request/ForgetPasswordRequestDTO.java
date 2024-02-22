package tech.olatunbosun.wastemanagement.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequestDTO {

    @Email(message = "Email should be valid")
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}

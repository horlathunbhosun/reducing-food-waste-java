package tech.olatunbosun.wastemanagement.usermanagement.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.olatunbosun.wastemanagement.validation.ValidEmail;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResendVerificationRequestDTO {

    @ValidEmail
    @JsonProperty("email")
    private String email;
}

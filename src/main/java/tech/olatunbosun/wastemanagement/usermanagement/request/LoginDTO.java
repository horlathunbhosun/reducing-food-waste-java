package tech.olatunbosun.wastemanagement.usermanagement.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @JsonProperty("password")
    private String password;
}

package tech.olatunbosun.wastemanagement.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * @author olulodeolatunbosun
 * @created 08/03/2024/03/2024 - 16:38
 */

@Data
@Builder
public class ChangePasswordDTO {



    @NotBlank(message = "Current Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank(message = "New Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @JsonProperty("new_password")
    private String newPassword;

    @NotBlank(message = "Confirmation Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @JsonProperty("confirmation_password")
    private String confirmationPassword;

}

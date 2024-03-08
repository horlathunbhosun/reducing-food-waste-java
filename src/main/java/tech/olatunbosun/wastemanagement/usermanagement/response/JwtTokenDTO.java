package tech.olatunbosun.wastemanagement.usermanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author olulodeolatunbosun
 * @created 08/03/2024/03/2024 - 17:39
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDTO {


    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;


    public static JwtTokenDTO data(String token, String tokenType, int expiresIn, String refreshToken) {
        return JwtTokenDTO.builder()
                .token(token)
                .tokenType(tokenType)
                .expiresIn(expiresIn)
                .refreshToken(refreshToken)
                .build();
    }

}

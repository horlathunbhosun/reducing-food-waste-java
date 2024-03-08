package tech.olatunbosun.wastemanagement.usermanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GenericResponseDTO {

    private String message;
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    private Object data;
    private JwtTokenDTO token;
    @JsonProperty("error_data")
    private Object errorData;
    @JsonProperty("error_message")
    private Map<String, String> errorMessage;
}

package tech.olatunbosun.wastemanagement.usermanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    private String token;
    @JsonProperty("error_data")
    private Object errorData;
}

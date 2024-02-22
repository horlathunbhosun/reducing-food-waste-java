package tech.olatunbosun.wastemanagement.usermanagement.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponseDTO {

    private String message;
    private String status;
    private String statusCode;
    private Object data;
    @JsonProperty("error_data")
    private Object errorData;
}

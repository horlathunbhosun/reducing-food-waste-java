package tech.olatunbosun.wastemanagement.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author olulodeolatunbosun
 * @created 08/03/2024/03/2024 - 18:19
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {

    @JsonProperty("brn_number")
    private String BRNumber;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("address")
    private String address;
}

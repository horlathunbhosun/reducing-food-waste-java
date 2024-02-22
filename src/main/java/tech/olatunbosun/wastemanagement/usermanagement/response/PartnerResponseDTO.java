package tech.olatunbosun.wastemanagement.usermanagement.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartnerResponseDTO {
    private Long id;
    private String brNumber;
    private String logo;
    private String address;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
}

package tech.olatunbosun.wastemanagement.usermanagement.response;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerResponseDTO {
    private Long id;
    private String brNumber;
    private String logo;
    private String address;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
}

package tech.olatunbosun.wastemanagement.usermanagement.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerResponseDTO {
    private Integer id;
    private String brNumber;
    private String logo;
    private String address;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}

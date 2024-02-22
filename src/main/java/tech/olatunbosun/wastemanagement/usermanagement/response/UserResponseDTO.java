package tech.olatunbosun.wastemanagement.usermanagement.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String verified;
    private String status;
    private String userType;
    private PartnerResponseDTO partner;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
}

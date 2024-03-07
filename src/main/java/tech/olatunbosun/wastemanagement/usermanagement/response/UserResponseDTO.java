package tech.olatunbosun.wastemanagement.usermanagement.response;


import lombok.*;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean verified;
    private String status;
    private String userType;
    private PartnerResponseDTO partner;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;

    public static UserResponseDTO userResponseBuilder(User savedUser) {
         return UserResponseDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .phoneNumber(savedUser.getPhoneNumber())
                .userType(String.valueOf(savedUser.getUserType()))
                .verified(savedUser.isVerified())
                .status(String.valueOf(savedUser.getStatus()))
//                 .partner(savedUser.getPartner() != null ? PartnerResponseDTO.builder().id(savedUser.getPartner().getId()).build() : null)
                .dateCreated(savedUser.getDateCreated())
                .dateUpdated(savedUser.getDateUpdated())
                .build();
    }

}

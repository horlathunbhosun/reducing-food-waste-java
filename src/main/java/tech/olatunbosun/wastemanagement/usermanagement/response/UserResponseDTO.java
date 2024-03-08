package tech.olatunbosun.wastemanagement.usermanagement.response;


import lombok.*;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean verified;
    private String status;
    private String userType;
    private PartnerResponseDTO partner;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;

    public static UserResponseDTO userResponseBuilder(User savedUser, Partner partner) {
            PartnerResponseDTO partnerResponseDTO = null;
            if (partner != null) {
                partnerResponseDTO = PartnerResponseDTO.builder()
                        .id(partner.getId())
                        .brNumber(partner.getBrNumber())
                        .logo(partner.getLogo())
                        .address(partner.getAddress())
                        .dateCreated(partner.getDateCreated())
                        .dateUpdated(partner.getDateUpdated())
                        .build();
            }

            return UserResponseDTO.builder()
                    .id(savedUser.getId())
                    .email(savedUser.getEmail())
                    .fullName(savedUser.getFullName())
                    .phoneNumber(savedUser.getPhoneNumber())
                    .userType(String.valueOf(savedUser.getUserType()))
                    .verified(savedUser.isVerified())
                    .status(String.valueOf(savedUser.getStatus()))
                    .partner(partnerResponseDTO)
                    .dateCreated(savedUser.getDateCreated())
                    .dateUpdated(savedUser.getDateUpdated())
                    .build();
        }



}

package tech.olatunbosun.wastemanagement.usermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserStatus;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

import java.time.LocalDate;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)

    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verification_code", unique = true)
    private String verificationCode;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    @Column(name = "is_verified", nullable = false)
    private boolean verified;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;





}

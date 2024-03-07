package tech.olatunbosun.wastemanagement.usermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserStatus;
import tech.olatunbosun.wastemanagement.usermanagement.utility.enums.UserType;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User implements UserDetails {

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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

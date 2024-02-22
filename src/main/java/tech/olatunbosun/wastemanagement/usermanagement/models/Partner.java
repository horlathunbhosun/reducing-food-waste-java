package tech.olatunbosun.wastemanagement.usermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partners")
public class Partner {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Null
    @Column(name = "brn_number", nullable = true)
    private String brNumber;
    @Null
    @Column(name = "logo", nullable = true)
    private String logo;
    @Null
    @Column(name = "address", nullable = true)
    private String address;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;


}

package tech.olatunbosun.wastemanagement.usermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Null
    @Column(name = "brn_number", nullable = true)
    private String BRNumber;
    @Null
    @Column(name = "logo", nullable = true)
    private String Logo;
    @Null
    @Column(name = "address", nullable = true)
    private String Address;
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Column(name = "date_updated", nullable = false)
    private LocalDate dateUpdated;


}

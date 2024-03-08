package tech.olatunbosun.wastemanagement.usermanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tech.olatunbosun.wastemanagement.management.models.MagicBag;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partners")
public class Partner {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    @Null
    @Column(name = "brn_number", nullable = true)
    private String brNumber;
    @Null
    @Column(name = "logo", nullable = true)
    private String logo;
    @Null
    @Column(name = "address", nullable = true)
    private String address;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner")
    private List<MagicBag> magicBags;


    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;


}

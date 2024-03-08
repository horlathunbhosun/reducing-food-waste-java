package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import tech.olatunbosun.wastemanagement.usermanagement.models.Partner;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "magic_bags")
public class MagicBag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "magicBag")
    private List<MagicBagItem> magicBagItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "magicBag")
    private List<Transaction> transactions;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;

}

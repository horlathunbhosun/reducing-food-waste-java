package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "magic_bags")
public class MagicBag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;

    @Column(name = "partner_id", nullable = false)
    private int partnerId;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Column(name = "date_updated", nullable = false)
    private LocalDate dateUpdated;

}

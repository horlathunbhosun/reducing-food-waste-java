package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "magic_bag_items")
public class MagicBagItem {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "magic_bag_id", nullable = false)
    private Long magicBagId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Column(name = "date_updated", nullable = false)
    private LocalDate dateUpdated;
}

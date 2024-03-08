package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "magic_bag_items")
public class MagicBagItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "magic_bag_id")
    private MagicBag magicBag;

    @ManyToMany
    @JoinTable(
            name = "magic_bag_item_product",
            joinColumns = @JoinColumn(name = "magic_bag_item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;
}

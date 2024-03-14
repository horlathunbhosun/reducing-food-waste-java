package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @Null
    @Column(name = "description", nullable = true)
    private String description;


    @ManyToMany(mappedBy = "product")
    private List<MagicBagItem> magicBagItems;


    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "date_updated", nullable = true)
    @UpdateTimestamp
    private LocalDate dateUpdated;




}

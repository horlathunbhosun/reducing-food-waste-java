package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @Null
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Column(name = "date_updated", nullable = false)
    private LocalDate dateUpdated;




}

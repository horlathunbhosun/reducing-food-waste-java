package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.hibernate.annotations.CreationTimestamp;
import tech.olatunbosun.wastemanagement.management.utility.enums.PaymentType;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "magic_bag_id", nullable = false)
    private Long magicBagId;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;



}

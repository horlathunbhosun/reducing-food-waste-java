package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @NotNull
    @Column(name = "date_updated", nullable = false)
    private LocalDate dateUpdated;



}

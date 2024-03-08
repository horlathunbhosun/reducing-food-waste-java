package tech.olatunbosun.wastemanagement.management.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import tech.olatunbosun.wastemanagement.management.utility.enums.PaymentType;
import tech.olatunbosun.wastemanagement.usermanagement.models.User;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magic_bag_id")
    private MagicBag magicBag;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;
    @Null
    @Column(name = "date_updated", nullable = true)
    private LocalDate dateUpdated;



}

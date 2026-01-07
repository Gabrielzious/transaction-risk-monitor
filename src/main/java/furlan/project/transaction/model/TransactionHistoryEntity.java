package furlan.project.transaction.model;

import furlan.project.transaction.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Entity used to audit every status change in a transaction.
 */
@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
public class TransactionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private TransactionStatus previousStatus; // Nullable for the very first record (CREATED)

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private TransactionStatus newStatus;

    @Column(name = "observation")
    private String observation; // Optional notes about the change

    @Column(name = "responsible_user")
    private String responsibleUser; // System or Admin username

    @PrePersist
    protected void onCreate() {
        this.changeDate = LocalDateTime.now();
    }
}
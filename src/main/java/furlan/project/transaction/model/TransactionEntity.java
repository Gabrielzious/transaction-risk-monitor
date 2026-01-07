package furlan.project.transaction.model;

import furlan.project.transaction.enums.TransactionStatus; // New Enum
import furlan.project.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * External Identifier (UUID).
     * Good practice: Don't expose your Database Incremental ID to the public API/Frontend.
     */
    @Column(name = "external_id", unique = true, nullable = false)
    private String externalId = UUID.randomUUID().toString();

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "amount", nullable = false) // "amount" is more standard than "value"
    private Double amount;

    /**
     * Currency code (ISO 4217) like 'USD', 'BRL', 'EUR'.
     * Crucial for cross-border transactions.
     */
    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "origin_country", length = 50)
    private String originCountry;

    @Column(name = "destination_country", length = 50)
    private String destinationCountry;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    /**
     * Current state of the transaction.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    /**
     * Audit fields to know when the record was created/updated.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.transactionDate == null) this.transactionDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
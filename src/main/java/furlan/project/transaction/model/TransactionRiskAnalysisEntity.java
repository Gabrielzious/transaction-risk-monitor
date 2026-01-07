package furlan.project.transaction.model;

import furlan.project.transaction.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Specialized entity that holds risk assessment details for a specific transaction.
 * This record exists only while a transaction is under scrutiny or identified as risky.
 */
@Entity
@Table(name = "transaction_risk_analysis")
@Getter
@Setter
@NoArgsConstructor
public class TransactionRiskAnalysisEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "transaction_id")
    private TransactionEntity transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevel riskLevel;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "flagged_reason", length = 500) // Changed from analysis_reason to flagged_reason
    private String flaggedReason;

    @Column(name = "antifraud_code")
    private String antifraudCode;

    @Column(name = "requires_manual_review")
    private Boolean requiresManualReview = false;

    @Column(name = "flagged_at") // More specific than arrival_at_queue
    private LocalDateTime flaggedAt;

    @PrePersist
    protected void onCreate() {
        this.flaggedAt = LocalDateTime.now();
    }
}
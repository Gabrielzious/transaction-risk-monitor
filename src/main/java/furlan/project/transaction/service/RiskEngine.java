package furlan.project.transaction.service;

import furlan.project.transaction.config.RiskRulesConfig;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service responsible for the scoring logic. It fetches raw data from RiskRulesConfig
 * and processes it using functional programming.
 */
@Component
@RequiredArgsConstructor
public class RiskEngine {

    private final RiskRulesConfig riskConfig;

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Main entry point for risk calculation.
     */
    public Integer evaluate(TransactionEntity transaction) {
        int score = 0;
        score += calculateAmountScore(transaction.getAmount());
        // Rule 2: Time based score (Duplicates)
        score += calculateTimeScore(transaction);
        // Additional rules (time, type) will be added here
        return score;
    }

    public RiskLevel classify(Integer totalScore) {
        return riskConfig.getClassification().getLevels().entrySet().stream()
                .filter(entry -> totalScore >= entry.getValue())
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> RiskLevel.valueOf(entry.getKey().toUpperCase()))
                .findFirst()
                .orElse(RiskLevel.LOW);
    }

    private Integer calculateAmountScore(Double amount) {
        return riskConfig.getRules().getAmount().getThresholds().entrySet().stream()
                .filter(entry -> amount >= Double.parseDouble(entry.getKey()))
                .map(Map.Entry::getValue)
                .max(Integer::compare)
                .orElse(0);
    }

    /**
     * Calculates penalty points if the customer has recent transactions.
     * Uses the time interval and penalty points defined in the configuration.
     */
    private Integer calculateTimeScore(TransactionEntity transaction) {
        // Get configuration values
        Integer interval = riskConfig.getRules().getTime().getIntervalMinutes();
        Integer penalty = riskConfig.getRules().getTime().getDuplicatePenalty();

        // Calculate the threshold time
        LocalDateTime limit = LocalDateTime.now().minusMinutes(interval);

        // Check database for recent transactions from this customer
        List<TransactionEntity> recentTransactions =
                transactionRepository.findByCustomerIdAndCreatedAtAfter(transaction.getCustomerId(), limit);

        // If the list is not empty, it means there are duplicate/frequent transactions
        return recentTransactions.isEmpty() ? 0 : penalty;
    }
}
package furlan.project.transaction.alert.impl;

import furlan.project.transaction.alert.AlertService;
import furlan.project.transaction.dto.RiskResult;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.model.TransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsoleAlertService implements AlertService {

    @Override
    public void sendAlert(TransactionEntity transaction, RiskResult riskResult) {
        // Only process alerts that are above LOW risk level
        if (riskResult.getLevel() == RiskLevel.LOW) {
            return;
        }

        // Standard console logging for suspicious transactions
        log.warn("ALERT: High Risk Transaction Detected!");
        log.warn("Transaction ID: {} | Risk Level: {} | Score: {}",
                transaction.getId(),
                riskResult.getLevel(),
                riskResult.getScore());

        log.warn("Amount: {} | Client ID: {}",
                transaction.getAmount(),
                transaction.getCustomerId());
    }
}
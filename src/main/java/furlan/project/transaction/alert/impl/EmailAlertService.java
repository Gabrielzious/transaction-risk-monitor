package furlan.project.transaction.alert.impl;

import furlan.project.transaction.alert.AlertService;
import furlan.project.transaction.dto.RiskResult;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.model.TransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmailAlertService implements AlertService {

    // Define which levels are critical enough for an email notification
    private static final List<RiskLevel> CRITICAL_LEVELS = List.of(RiskLevel.HIGH, RiskLevel.CRITICAL);

    @Override
    public void sendAlert(TransactionEntity transaction, RiskResult riskResult) {
        // Only send email for High or Critical risk levels to avoid inbox cluttering
        if (!CRITICAL_LEVELS.contains(riskResult.getLevel())) {
            return;
        }

        // MOCKED: This is a placeholder for actual email sending logic
        // In a real scenario, we would inject a JavaMailSender or use a 3rd party API
        simulateEmailSending(transaction, riskResult);
    }

    private void simulateEmailSending(TransactionEntity transaction, RiskResult riskResult) {
        log.info("MOCK EMAIL SERVICE: Preparing to send message to compliance@company.com");

        String emailBody = String.format(
                "ALERT: Suspicious activity from Customer [%s]. Risk Score: %d (%s). Transaction Amount: %.2f",
                transaction.getCustomerId(),
                riskResult.getScore(),
                riskResult.getLevel(),
                transaction.getAmount()
        );

        // MOCKED: Logging represents the email being delivered to the SMTP server
        log.info("MOCK EMAIL SERVICE: Message successfully 'sent'. Content: {}", emailBody);
    }
}
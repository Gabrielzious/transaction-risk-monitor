package furlan.project.transaction.alert;

import furlan.project.transaction.dto.RiskResult;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.model.TransactionEntity;

public interface AlertService {

    void sendAlert(TransactionEntity transaction, RiskResult riskResult);
}

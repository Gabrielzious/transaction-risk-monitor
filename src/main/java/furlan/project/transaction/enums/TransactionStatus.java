package furlan.project.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    CREATED("Transaction received by the system"),
    PENDING_RISK("Sent to manual or automatic risk analysis"),
    APPROVED("Transaction cleared and payment confirmed"),
    DECLINED("Transaction rejected due to risk or lack of funds"),
    CANCELLED("Transaction aborted by the user or merchant");

    private final String description;
}
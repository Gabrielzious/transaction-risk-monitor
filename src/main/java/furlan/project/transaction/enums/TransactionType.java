package furlan.project.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    TRANSFER,
    PAYMENT,
    DEPOSIT,
    WITHDRAWAL,
    REFUND,
    CASHBACK
}
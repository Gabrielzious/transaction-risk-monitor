package furlan.project.transaction.factory;

import furlan.project.transaction.model.TransactionEntity;
import java.time.LocalDateTime;

public class TransactionFactory {

    // Common valid transaction
    public static TransactionEntity createValidTransaction(String customerId) {
        return TransactionEntity.builder()
                .customerId(customerId)
                .amount(100.0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // High value transaction to trigger amount rules
    public static TransactionEntity createCriticalTransaction(String customerId) {
        return TransactionEntity.builder()
                .customerId(customerId)
                .amount(50000.0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Transaction with specific date to test time rules (duplicate/frequency)
    public static TransactionEntity createTransactionAt(String customerId, LocalDateTime time) {
        return TransactionEntity.builder()
                .customerId(customerId)
                .amount(150.0)
                .createdAt(time)
                .build();
    }
}
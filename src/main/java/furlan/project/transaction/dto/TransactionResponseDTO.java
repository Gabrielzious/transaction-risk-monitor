package furlan.project.transaction.dto;

import furlan.project.transaction.enums.TransactionStatus;
import furlan.project.transaction.enums.TransactionType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private String externalId;
    private LocalDateTime transactionDate;
    private Double amount;
    private String currency;
    private String originCountry;
    private String destinationCountry;
    private TransactionType type;
    private TransactionStatus status;
}
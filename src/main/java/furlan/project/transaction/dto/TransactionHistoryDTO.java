package furlan.project.transaction.dto;

import furlan.project.transaction.enums.TransactionStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionHistoryDTO {
    private LocalDateTime changeDate;
    private TransactionStatus previousStatus;
    private TransactionStatus newStatus;
    private String observation;
    private String responsibleUser;
}
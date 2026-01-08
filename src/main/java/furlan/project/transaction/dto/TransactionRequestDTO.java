package furlan.project.transaction.dto;

import furlan.project.transaction.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionRequestDTO {

    // Optional: If the customer wants, he may offer the internal Id, for tracking.
    private String externalId;

    // Optional: if it's not given, the system will automatically fill as "LocalDateTime.now()"
    private LocalDateTime transactionDate;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotNull(message = "customer id is mandatory")
    private String customerId;

    @NotBlank(message = "Currency is mandatory")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters (ISO 4217)")
    private String currency;

    @NotBlank(message = "Origin country is mandatory")
    private String originCountry;

    @NotBlank(message = "Destination country is mandatory")
    private String destinationCountry;

    @NotNull(message = "Transaction type is mandatory")
    private TransactionType type;
}
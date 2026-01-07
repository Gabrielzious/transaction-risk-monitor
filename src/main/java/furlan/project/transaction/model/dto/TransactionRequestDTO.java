package furlan.project.transaction.model.dto;


import furlan.project.transaction.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequestDTO {
    @NotNull(message = "ID cannot be null")
    Long id;

    @NotNull(message = "Date is required")
    LocalDateTime transactionDate;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be greater than zero")
    Double value;

    @NotBlank(message = "Origin country is required")
    String originCountry;

    @NotBlank(message = "Destination country is required")
    String destinationCountry;

    @NotNull(message = "Transaction type is required")
    TransactionType type;

}

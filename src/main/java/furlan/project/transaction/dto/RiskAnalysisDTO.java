package furlan.project.transaction.dto;

import furlan.project.transaction.enums.RiskLevel;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RiskAnalysisDTO {

    @NotNull(message = "Risk level is mandatory")
    private RiskLevel riskLevel;

    @Min(0) @Max(100)
    private Double riskScore;

    @Size(max = 500)
    private String flaggedReason;

    private String antifraudCode;

    private Boolean requiresManualReview;
}
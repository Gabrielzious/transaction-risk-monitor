package furlan.project.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevel {
    LOW("Low risk - Automatic approval"),
    MEDIUM("Moderate risk - Needs attention"),
    HIGH("High risk - Verification required"),
    CRITICAL("Critical risk - Preventive block");

    private final String description;
}
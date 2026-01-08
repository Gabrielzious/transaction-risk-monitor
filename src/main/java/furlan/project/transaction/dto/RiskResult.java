package furlan.project.transaction.dto;

import furlan.project.transaction.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiskResult {
    private final RiskLevel level;
    private final Integer score;
}

package furlan.project.transaction.config;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * Holds data structures for various risk-scoring rules.
 */
@Getter
@Setter
public class RulesConfig {
    private AmountRules amount;
    private TypeRules type;
    private TimeRules time;

    @Getter @Setter
    public static class AmountRules {
        private Map<String, Integer> thresholds;
    }

    @Getter @Setter
    public static class TypeRules {
        private Map<String, Integer> weights;
    }

    @Getter @Setter
    public static class TimeRules {
        private Integer intervalMinutes;
        private Integer duplicatePenalty;
    }
}
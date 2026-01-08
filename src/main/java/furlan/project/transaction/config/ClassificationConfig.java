package furlan.project.transaction.config;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

/**
 * Data holder for final risk level classification ranges.
 */
@Getter
@Setter
public class ClassificationConfig {
    private Map<String, Integer> levels;
}
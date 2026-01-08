package furlan.project.transaction.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Root configuration class for the risk engine.
 * Maps the 'risk' prefix from application.properties.
 */
@Configuration
@ConfigurationProperties(prefix = "risk")
@Getter
@Setter
public class RiskRulesConfig {
    private RulesConfig rules;
    private ClassificationConfig classification;
}
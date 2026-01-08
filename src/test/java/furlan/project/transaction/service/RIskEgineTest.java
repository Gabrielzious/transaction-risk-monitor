package furlan.project.transaction.service;

import furlan.project.transaction.config.ClassificationConfig;
import furlan.project.transaction.config.RiskRulesConfig;
import furlan.project.transaction.config.RulesConfig;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.factory.TransactionFactory;
import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiskEngineTest {

    @Mock
    private RiskRulesConfig riskConfig;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RiskEngine riskEngine;

    @BeforeEach
    void setup() {
        // 1. Manually inject mocks into the engine (Fixes the NullPointerException on @Autowired fields)
        org.springframework.test.util.ReflectionTestUtils.setField(riskEngine, "transactionRepository", transactionRepository);
        org.springframework.test.util.ReflectionTestUtils.setField(riskEngine, "riskConfig", riskConfig);

        // 2. Instantiate the main RulesConfig container
        RulesConfig rules = new RulesConfig();

        // 3. Instantiate the nested static classes
        RulesConfig.AmountRules amountRule = new RulesConfig.AmountRules();
        RulesConfig.TimeRules timeRule = new RulesConfig.TimeRules();

        // 4. Setup Amount Thresholds
        java.util.Map<String, Integer> thresholds = new java.util.TreeMap<>();
        thresholds.put("1000", 10);
        thresholds.put("5000", 50);
        amountRule.setThresholds(thresholds);

        // 5. Setup Time Rules
        timeRule.setIntervalMinutes(5);
        timeRule.setDuplicatePenalty(30);

        // 6. Link the rules to the main config object
        rules.setAmount(amountRule);
        rules.setTime(timeRule);

        // 7. Setup Classification levels
        ClassificationConfig classification = new ClassificationConfig();
        java.util.Map<String, Integer> levels = new java.util.TreeMap<>();
        levels.put("low", 0);
        levels.put("medium", 40);
        levels.put("high", 80);
        classification.setLevels(levels);

        // 8. Configure Mockito with 'lenient' to avoid UnnecessaryStubbingException
        org.mockito.Mockito.lenient().when(riskConfig.getRules()).thenReturn(rules);
        org.mockito.Mockito.lenient().when(riskConfig.getClassification()).thenReturn(classification);
    }

    @Test
    @DisplayName("Should return score based only on amount when no duplicates exist")
    void evaluateAmountOnly() {
        // Arrange
        TransactionEntity tx = TransactionFactory.createValidTransaction("cust-1");
        tx.setAmount(2000.0); // Should match "1000" threshold (10 points)

        when(transactionRepository.findByCustomerIdAndCreatedAtAfter(eq("cust-1"), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        Integer score = riskEngine.evaluate(tx);

        // Assert
        assertEquals(10, score);
    }

    @Test
    @DisplayName("Should add penalty points when duplicate transactions are found")
    void evaluateWithTimePenalty() {
        // Arrange
        TransactionEntity tx = TransactionFactory.createValidTransaction("cust-1");
        tx.setAmount(500.0); // Amount below thresholds (0 points)

        // Mocking a previous transaction found in the database
        when(transactionRepository.findByCustomerIdAndCreatedAtAfter(eq("cust-1"), any(LocalDateTime.class)))
                .thenReturn(List.of(new TransactionEntity()));

        // Act
        Integer score = riskEngine.evaluate(tx);

        // Assert
        assertEquals(30, score); // 0 (amount) + 30 (time penalty)
    }

    @Test
    @DisplayName("Should classify score into correct RiskLevel")
    void classifyCorrectly() {
        // Assertions based on mocked levels (Medium >= 40, High >= 80)
        assertEquals(RiskLevel.LOW, riskEngine.classify(10));
        assertEquals(RiskLevel.MEDIUM, riskEngine.classify(45));
        assertEquals(RiskLevel.HIGH, riskEngine.classify(85));
    }
}
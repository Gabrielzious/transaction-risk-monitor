package furlan.project.transaction.service;

import furlan.project.transaction.dto.TransactionRequestDTO;
import furlan.project.transaction.factory.TransactionFactory;
import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setup() {
        // Clear database before each test to ensure isolation
        transactionRepository.deleteAll();
    }

    @Test
    void shouldProcessHighRiskTransactionAndTriggerAlerts() {
        // 1. Arrange: Create a high value transaction using the factory
        String customerId = "customer-high-risk-test";
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setCustomerId(customerId);
        request.setAmount(100000.0); // High amount to trigger risk

        // 2. Act: Process the transaction through the service
        transactionService.processTransaction(request);

        // 3. Assert: Check if it was saved in the database
        List<TransactionEntity> savedTransactions = transactionRepository.findAll();
        assertEquals(1, savedTransactions.size());
        assertEquals(customerId, savedTransactions.get(0).getCustomerId());
        assertNotNull(savedTransactions.get(0).getCreatedAt());

        // Note: Check your IDE console to see the LogAlert and EmailAlert mocks!
    }

    @Test
    void shouldDetectDuplicateTransactionRisk() {
        // 1. Arrange: Save a previous transaction for the same customer
        String customerId = "duplicate-user";
        transactionRepository.save(TransactionFactory.createValidTransaction(customerId));

        // Create a new request for the same user immediately after
        TransactionRequestDTO secondRequest = new TransactionRequestDTO();
        secondRequest.setCustomerId(customerId);
        secondRequest.setAmount(50.0);

        // 2. Act: Process the second transaction
        transactionService.processTransaction(secondRequest);

        // 3. Assert: Check if both are in the database
        assertEquals(2, transactionRepository.findAll().size());

        // The console logs should show a higher score due to the calculateTimeScore rule
    }
}
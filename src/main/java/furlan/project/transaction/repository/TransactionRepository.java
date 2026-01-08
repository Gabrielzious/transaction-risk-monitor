package furlan.project.transaction.repository;

import furlan.project.transaction.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for TransactionEntity.
 * Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    // Derived query to find transactions by customer within a timeframe
    List<TransactionEntity> findByCustomerIdAndCreatedAtAfter(String customerId, LocalDateTime timestamp);
}
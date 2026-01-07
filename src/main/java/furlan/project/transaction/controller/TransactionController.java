package furlan.project.transaction.controller;

import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionEntity> postTransaction (@Valid @RequestBody TransactionEntity transaction){


        return ResponseEntity.ok(transaction);
    }
}
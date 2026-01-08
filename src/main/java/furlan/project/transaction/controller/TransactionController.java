package furlan.project.transaction.controller;

import furlan.project.transaction.dto.TransactionRequestDTO;
import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionEntity> postTransaction (@Valid @RequestBody TransactionRequestDTO transaction){

        transactionService.processTransaction(transaction);
        return null;

    }
}
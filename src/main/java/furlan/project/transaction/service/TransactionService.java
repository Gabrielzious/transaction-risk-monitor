package furlan.project.transaction.service;

import furlan.project.transaction.alert.AlertService;
import furlan.project.transaction.dto.RiskResult;
import furlan.project.transaction.dto.TransactionRequestDTO;
import furlan.project.transaction.enums.RiskLevel;
import furlan.project.transaction.enums.TransactionStatus;
import furlan.project.transaction.mapper.TransactionMapper;
import furlan.project.transaction.model.TransactionEntity;
import furlan.project.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static furlan.project.transaction.enums.TransactionStatus.CREATED;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private RiskEngine riskEngine;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private List<AlertService> alertServices;

    public void processTransaction (TransactionRequestDTO transactionRequestDTO) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transactionRequestDTO);
        Integer score = riskEngine.evaluate(transactionEntity);
        RiskLevel riskLevel = riskEngine.classify(score);
        
        RiskResult riskResult = new RiskResult(riskLevel, score);

        transactionEntity.setStatus(CREATED);
        TransactionEntity entity = transactionRepository.save(transactionEntity);
        alertServices.forEach(x -> x.sendAlert(entity, riskResult));

    }
}

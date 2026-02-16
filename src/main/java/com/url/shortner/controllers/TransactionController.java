package com.url.shortner.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.url.shortner.dto.ApiResponse;
import com.url.shortner.dto.CreateTransactionRequest;
import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionDetailsResponse;
import com.url.shortner.dto.TransactionResponse;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Transaction;
import com.url.shortner.mapper.TransactionMapper;
import com.url.shortner.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
            @RequestBody @Valid CreateTransactionRequest request) {

        Transaction txn = transactionService.createTransaction(
                request.accountId(),
                request.amount(),
                request.txnType()
        );

        TransactionResponse response =
                TransactionMapper.toResponse(txn);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Transaction successful", response));
    }
    
    
    
    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<Page<TransactionAccountView>> getTransactions(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                transactionService.getSuccessfulTransactions(accountId, page, size)
        );
    }
    
    
    @GetMapping("/accounts/{id}/total")
    public ResponseEntity<BigDecimal> getTotal(
            @PathVariable Long id,
            @RequestParam TransactionType type) {

        return ResponseEntity.ok(transactionService.getTotalAmount(id, type));

    }


}

package com.url.shortner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.url.shortner.dto.ApiResponse;
import com.url.shortner.dto.CreateTransactionRequest;
import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionDetailsResponse;
import com.url.shortner.dto.TransactionResponse;
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
    public ResponseEntity<ApiResponse<List<TransactionDetailsResponse>>> 
    getTransactionsByAccountId(@PathVariable Long accountId) {

        List<TransactionAccountView> transactions =
                transactionService.getSuccessfulTransactions(accountId);

        List<TransactionDetailsResponse> responseList =
                transactions.stream()
                        .map(TransactionMapper::fromView)
                        .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Transactions fetched successfully", responseList)
        );
    }

    }




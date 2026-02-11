package com.url.shortner.mapper;

import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionDetailsResponse;
import com.url.shortner.dto.TransactionResponse;
import com.url.shortner.dto.TransactionStatus;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Transaction;

public final class TransactionMapper {

    private TransactionMapper() {
    }

    
    public static TransactionResponse toResponse(Transaction txn) {

        if (txn == null) {
            return null;
        }

        Long accountId = txn.getAccount() != null
                ? txn.getAccount().getAccountId()
                : null;

        return new TransactionResponse(
                txn.getTxnId(),
                accountId,
                txn.getTxnType(),
                txn.getAmount(),
                txn.getStatus(),
                txn.getTxnDate(),
                txn.getBalanceAfter()
        );
    }

    
    public static TransactionDetailsResponse fromView(TransactionAccountView view) {

        return new TransactionDetailsResponse(
                view.getTxnId(),
                view.getAccountId(),
                TransactionType.valueOf(view.getTxnType()),
                view.getAmount(),
                TransactionStatus.valueOf(view.getStatus()),
                view.getTxnDate(),
                view.getBalanceAfter(),
                view.getHolderName(),
                view.getCustomerEmail()
        );
    }


    

}

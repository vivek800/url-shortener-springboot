package com.url.shortner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction ,Long> {

	@Query(value = """
		    SELECT 
		        t.txn_id        AS txnId,
		        t.amount        AS amount,
		        t.txn_date      AS txnDate,
		        t.txn_type      AS txnType,
		        t.status        AS status,
		        t.balance_after AS balanceAfter,
		        a.account_id    AS accountId,
		        c.name          AS holderName,
		        c.email         AS customerEmail
		    FROM transactions t
		    JOIN account a ON t.account_id = a.account_id
		    JOIN customer c ON a.customer_id = c.customer_id
		    WHERE t.account_id = :accountId
		      AND t.status = 'SUCCESS'
		""", nativeQuery = true)
		List<TransactionAccountView> findSuccessfulTxnsWithAccount(
		        @Param("accountId") Long accountId
		);



	@Query(
		    value = """
		        SELECT *
		        FROM transactions
		        WHERE account_id = :accountId
		        """,
		    nativeQuery = true
		)
		List<Transaction> findByAccountId(@Param("accountId") Long accountId);


}

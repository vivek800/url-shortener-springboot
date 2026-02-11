package com.url.shortner.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.url.shortner.dto.AccountStatus;
import com.url.shortner.dto.AccountType;

@Entity
@Table(
	    name = "account",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            columnNames = {"customer_id", "account_type"}
	        )
	    }
	)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    public Long getAccountId() {
		return accountId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_type")
    private AccountType accountType;  

    private BigDecimal balance;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;  

    public Long newBalance() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public LocalDate getOpenedDate() {
		return openedDate;
	}

	public void setOpenedDate(LocalDate openedDate) {
		this.openedDate = openedDate;
	}

	@Column(name = "opened_date")
    private LocalDate openedDate;

     
}

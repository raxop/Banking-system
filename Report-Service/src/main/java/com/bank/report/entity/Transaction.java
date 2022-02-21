package com.bank.report.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long transactionId;
	
	@Column(name="accountNumber")
	private String accountNumber;
	
	@Column(name="customerId")
	private String customerId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="newBalance")
	private int newBalance;
	
	@Column(name="currentBalance")
	private int currentBalance;
	
	
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(int newBalance) {
		this.newBalance = newBalance;
	}

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", customerId="
				+ customerId + ", status=" + status + ", amount=" + amount + ", newBalance=" + newBalance
				+ ", currentBalance=" + currentBalance + ", date=" + date + "]";
	}
	
	
	
	
	
	
}








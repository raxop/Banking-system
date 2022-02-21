package com.bank.account.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="BankInformation")
public class BankInformation {
	
	@Id
	@Column(name="accountNumber")
	private int accountNumber;
	
	@Column(name="accountType")
	private String accountType;
	
	@Column(name="customerId")
	private int customerId;
	
	@Column(name="balance")
	private int balance;

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}



	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BankInformation [accountNumber=" + accountNumber + ", accountType=" + accountType + ", customerId="
				+ customerId + ", balance=" + balance + "]";
	}
	
	

}

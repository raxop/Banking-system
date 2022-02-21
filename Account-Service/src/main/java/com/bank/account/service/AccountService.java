package com.bank.account.service;

import java.util.Date;
import java.util.List;

import com.bank.account.entity.BankInformation;
import com.bank.account.entity.Transaction;

public interface AccountService {

	BankInformation saveBankDetails(BankInformation bankInformation);
	List<BankInformation> getAllBankDetails();	
	BankInformation getBankDetailsById(int accountNumber);
	BankInformation getBankDetailsByCustomerId(int customerId);
	BankInformation updateBankDetails(BankInformation bankInformation,int accountNumber);
	BankInformation updateBankDetailsByCustomerId(BankInformation bankInformation, int accountNumber);
	void deleteBankDetailsByAccountNumber(int accountNumber);
	void deleteBankDetailsByCustomerId(int customerId);
	
	Transaction saveTransaction(Transaction transaction);
	List<Transaction> getAllTransactions();	
	Transaction getTransactionById(long id);
	Transaction updateTransaction(Transaction transaction,long id);
	void deleteTransaction(long id);
	String withdrawBalance(Transaction transaction);
	Transaction depositBalance(Transaction transaction);

	List<Transaction> findByDate(int accountNumber,int year);
	List<Transaction> findByMonth(int accountNumber,int month);
	List<Transaction> findByTwoDates(int accountNumber,Date date1,Date date2);
	Long getTotalBalanceByMonth(int month);
	Long getTotalBalanceByYear(int month);
	List<Integer> getcustomerIdByMonth(int month);
	List<Integer> getcustomerIdByYear(int year);
	
}
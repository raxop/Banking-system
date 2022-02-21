package com.bank.account.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.entity.BankInformation;
import com.bank.account.entity.Transaction;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.repository.BankInformationRepository;
import com.bank.account.repository.TransactionRepository;
import com.bank.account.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private BankInformationRepository bankInformationRepository;
	
	
	public AccountServiceImpl() {
		super();
	}

	public AccountServiceImpl(TransactionRepository transactionRepository,
			BankInformationRepository bankInformationRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.bankInformationRepository = bankInformationRepository;
	}

	public AccountServiceImpl(BankInformationRepository bankInformationRepository) {
		super();
		this.bankInformationRepository = bankInformationRepository;
	}
	
	public AccountServiceImpl(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}
	
	
	@Override
	public BankInformation saveBankDetails(BankInformation bankInformation) {
		// TODO Auto-generated method stub
		return bankInformationRepository.save(bankInformation);
	}

	@Override
	public List<BankInformation> getAllBankDetails() {
		// TODO Auto-generated method stub
		return bankInformationRepository.findAll();
	}

	@Override
	public BankInformation getBankDetailsById(int accountNumber) {
		// TODO Auto-generated method stub
		Optional<BankInformation> bankInformation=bankInformationRepository.findById(accountNumber);
		if(bankInformation.isPresent())
		{
			return bankInformation.get();
		}
		else
		{
			
			 throw new ResourceNotFoundException("Bank Details","id",accountNumber); 
		}
	}
	

	@Override
	public BankInformation updateBankDetails(BankInformation bankInformation, int accountNumber) {
		// TODO Auto-generated method stub
		BankInformation existingBankDetail=bankInformationRepository.findById(accountNumber).orElseThrow(()->new ResourceNotFoundException("Bank Details","id",accountNumber));
		existingBankDetail.setAccountNumber(bankInformation.getAccountNumber());
		existingBankDetail.setAccountType(bankInformation.getAccountType());
		existingBankDetail.setCustomerId(bankInformation.getCustomerId());
		existingBankDetail.setBalance(bankInformation.getBalance());
		bankInformationRepository.save(existingBankDetail);
		return existingBankDetail;
	}

	public void deleteBankDetailsByAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		bankInformationRepository.findById(accountNumber).orElseThrow(()->new ResourceNotFoundException("Bank Details","id",accountNumber));
		bankInformationRepository.deleteById(accountNumber);
		
	}

	@Override
	public Transaction saveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}

	@Override
	public Transaction getTransactionById(long id) {
		// TODO Auto-generated method stub
		Optional<Transaction> transaction=transactionRepository.findById(id);
		if(transaction.isPresent())
		{
			return transaction.get();
		}
		else
		{
			
			 throw new ResourceNotFoundException("Transaction","id",id); 
		}
	}

	@Override
	public Transaction updateTransaction(Transaction transaction, long id) {
		// TODO Auto-generated method stub
		Transaction existingTransaction=transactionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction","id",id));
		//existingTransaction.setTransactionId(transaction.getTransactionId());
		existingTransaction.setAccountNumber(transaction.getAccountNumber());
		existingTransaction.setCustomerId(transaction.getCustomerId());
		existingTransaction.setStatus(transaction.getStatus());
		existingTransaction.setAmount(transaction.getAmount());
		existingTransaction.setNewBalance(transaction.getNewBalance());
		existingTransaction.setCurrentBalance(transaction.getCurrentBalance());
		existingTransaction.setDate(transaction.getDate());
		transactionRepository.save(existingTransaction);
		return existingTransaction;
	}

	@Override
	public void deleteTransaction(long id) {
		// TODO Auto-generated method stub
		transactionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transcation","id",id));
		transactionRepository.deleteById(id);
		
	}

	@Override
	
	public String withdrawBalance(Transaction transaction) {
		// TODO Auto-generated method stub
		
		
		int newBalance,balance;
		BankInformation bi=bankInformationRepository.getById(transaction.getAccountNumber());
		transaction.setCurrentBalance(bi.getBalance());
		newBalance=transaction.getCurrentBalance()-transaction.getAmount();
		balance=bi.getBalance();
		if(newBalance<0)
		{
			return "You do not have sufficient balance";
		}
		else
		{
			transaction.setCustomerId(bi.getCustomerId());
			transaction.setStatus("withdraw");
			transaction.setNewBalance(newBalance);
			transaction.setDate(new java.util.Date());
			bi.setBalance(newBalance);
			bankInformationRepository.save(bi);
			transactionRepository.save(transaction);
			return "Withdrawal successful";
		}
	}
	
	public Transaction depositBalance(Transaction transaction) {
		// TODO Auto-generated method stub
		
		int newBalance;
		BankInformation bi=bankInformationRepository.getById(transaction.getAccountNumber());
		transaction.setCurrentBalance(bi.getBalance());
		transaction.setCustomerId(bi.getCustomerId());
		transaction.setStatus("deposit");
		newBalance=transaction.getCurrentBalance()+transaction.getAmount();
		transaction.setNewBalance(newBalance);
		transaction.setDate(new java.util.Date());
		bi.setBalance(newBalance);
		bankInformationRepository.save(bi);
		return transactionRepository.save(transaction);
	}


	@Override

	public List<Transaction> findByDate(int accountNumber,int year) {
		// TODO Auto-generated method stub
		
		List<Transaction> list=transactionRepository.findByDate(accountNumber,year);
		System.out.println(list);
		return list;
	}
	
	public List<Transaction> findByMonth(int accountNumber,int month) {
		// TODO Auto-generated method stub
		
		List<Transaction> list=transactionRepository.findByMonth(accountNumber,month);
		System.out.println(list);
		return list;
	}
	
	public List<Transaction> findByTwoDates(int accountNumber,Date date1,Date date2) {
		// TODO Auto-generated method stub
		
		List<Transaction> list=transactionRepository.findByTwoDates(accountNumber,date1,date2);
		System.out.println(list);
		return list;
	}

	@Override
	
	public Long getTotalBalanceByMonth(int month) {
		// TODO Auto-generated method stub

		return transactionRepository.getTotalBalanceByMonth(month);
	}
	
	
	public Long getTotalBalanceByYear(int year) {
		// TODO Auto-generated method stub

		return transactionRepository.getTotalBalanceByYear(year);
	}

	@Override
	public BankInformation getBankDetailsByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		Optional<BankInformation> bankInformation=Optional.of(bankInformationRepository.getBankDetailsByCustomerId(customerId));
		if(bankInformation.isPresent())
		{
			return bankInformation.get();
		}
		else
		{
			
			 throw new ResourceNotFoundException("Bank Details","id",customerId); 
		}
	}

	@Override
	public BankInformation updateBankDetailsByCustomerId(BankInformation bankInformation, int customerId) {
		// TODO Auto-generated method stub
		BankInformation existingBankDetail=bankInformationRepository.getBankDetailsByCustomerId(customerId);
		existingBankDetail.setAccountNumber(bankInformation.getAccountNumber());
		existingBankDetail.setAccountType(bankInformation.getAccountType());
		existingBankDetail.setCustomerId(bankInformation.getCustomerId());
		existingBankDetail.setBalance(bankInformation.getBalance());
		bankInformationRepository.save(existingBankDetail);
		return existingBankDetail;
	}

	public void deleteBankDetailsByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		
	bankInformationRepository.deleteByCustomerId(customerId);
		
	}

	@Override
	public List<Integer> getcustomerIdByMonth(int month) {
		// TODO Auto-generated method stub
		return transactionRepository.getcustomerIdByMonth(month);
	}
	public List<Integer> getcustomerIdByYear(int year) {
		// TODO Auto-generated method stub
		return transactionRepository.getcustomerIdByYear(year);
	}

}
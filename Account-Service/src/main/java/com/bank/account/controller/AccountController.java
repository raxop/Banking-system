package com.bank.account.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
 

import com.bank.account.entity.BankInformation;
import com.bank.account.entity.Transaction;
import com.bank.account.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
	super();
	this.accountService = accountService;
	}
	
	@PostMapping("/Transaction/withdraw")
	public ResponseEntity<String> withdrawBalance(@RequestBody Transaction transaction )
	{
		String msg=accountService.withdrawBalance(transaction);
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	@PostMapping("/Transaction/deposit")
	public ResponseEntity<Transaction> depositBalance(@RequestBody Transaction transaction )
	{
		return new ResponseEntity<Transaction>(accountService.depositBalance(transaction),HttpStatus.CREATED);
	}
	
	@GetMapping("/Transaction/accountstatus/{accountNumber}/year/{year}")
	public List<Transaction> findByDate(@PathVariable("accountNumber") int accountNumber,@PathVariable("year") int year)
	{
		System.out.println(year);

		List<Transaction> list=accountService.findByDate(accountNumber,year);
		System.out.println(list);
		return list;		
	} 
	
	@GetMapping("/Transaction/accountstatus/{accountNumber}/month/{month}")
	public List<Transaction> findByMonth(@PathVariable("accountNumber") int accountNumber,@PathVariable("month") int month)
	{
		System.out.println(month);
		return accountService.findByMonth(accountNumber,month);		
	} 
	
	@GetMapping("/Transaction/accountstatus/{accountNumber}/date/{date1}/{date2}")
	public List<Transaction> findByTwoDates(@PathVariable("accountNumber") int accountNumber,@PathVariable("date1") String sdate1,@PathVariable("date2") String sdate2) throws ParseException
	{

		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sdate1);
		Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(sdate2);
		return accountService.findByTwoDates(accountNumber,date1,date2);		
	} 
	
	
	@PostMapping("/BankInformation")
	public ResponseEntity<BankInformation> saveBankDetails(@RequestBody BankInformation bankInformation)
	{
		return new ResponseEntity<BankInformation>(accountService.saveBankDetails(bankInformation),HttpStatus.CREATED);
	}
	
	@GetMapping("/BankInformation")
	public List<BankInformation> getAllBankDetails()
	{
		return accountService.getAllBankDetails();
	}
	
	@GetMapping("/BankInformation/{accountNumber}")
	public ResponseEntity<BankInformation> getBankDetailsById(@PathVariable("accountNumber") int accountNumber)
	{
		return new ResponseEntity<BankInformation>(accountService.getBankDetailsById(accountNumber),HttpStatus.OK);
	}
	
	@GetMapping("/BankInformation/customerid/{customerId}")
	public BankInformation getBankDetailsByCustomerId(@PathVariable("customerId") int customerId)
	{

		return accountService.getBankDetailsByCustomerId(customerId);
		
	}
	
	
	
	@PostMapping("/BankInformation/customerId/{customerId}")
	public ResponseEntity<BankInformation> updateBankDetailsByCustomerId(@PathVariable("customerId") int accountNumber,@RequestBody BankInformation bankInformation)
	{

		return new ResponseEntity<BankInformation>(accountService.updateBankDetailsByCustomerId(bankInformation,accountNumber),HttpStatus.OK);
		
	}
	
	@PutMapping("/BankInformation/{accountNumber}")
	public ResponseEntity<BankInformation> updateBankDetails(@PathVariable("accountNumber") int accountNumber,@RequestBody BankInformation bankInformation)
	{

		return new ResponseEntity<BankInformation>(accountService.updateBankDetails(bankInformation,accountNumber),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/BankInformation/{accountNumber}")
	public ResponseEntity<String> deleteBankDetails(@PathVariable("accountNumber") int accountNumber)
	{

		accountService.deleteBankDetailsByAccountNumber(accountNumber);
		return new ResponseEntity<String>("Account Deleted successfully",HttpStatus.OK);
		
	}
	
	@GetMapping("/BankInformation/delete/{customerId}")
	public String deleteBankDetailsByCustomerId(@PathVariable("customerId") int customerId)
	{

		accountService.deleteBankDetailsByCustomerId(customerId);
		return "Account Deleted successfully";
		
	}
	
	
	@PostMapping("/Transaction")
	public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction)
	{
		return new ResponseEntity<Transaction>(accountService.saveTransaction(transaction),HttpStatus.CREATED);
	
	}
	
	@GetMapping("/Transaction")
	public List<Transaction> getAllTransactions()
	{
		return accountService.getAllTransactions();
		
	}
	@GetMapping("/Transaction/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") long id)
	{

		return new ResponseEntity<Transaction>(accountService.getTransactionById(id),HttpStatus.OK);
		
	}
	
	@PutMapping("/Transaction/{id}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") long id,@RequestBody Transaction transaction)
	{

		return new ResponseEntity<Transaction>(accountService.updateTransaction(transaction,id),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/Transaction/{id}")
	public ResponseEntity<String> deleteTransaction(@PathVariable("id") long id)
	{

		accountService.deleteTransaction(id);
		return new ResponseEntity<String>("Transaction Deleted successfully",HttpStatus.OK);
		
	}
	
	@GetMapping("/report/month/customerId/{month}")
	public List<Integer> getcustomerIdByMonth(@PathVariable("month") int month)
	{
		return accountService.getcustomerIdByMonth(month) ;
		
	}
	
	@GetMapping("/report/year/customerId/{year}")
	public List<Integer> getcustomerIdByYear(@PathVariable("year") int year)
	{
		return accountService.getcustomerIdByYear(year) ;
		
	}
	
	@GetMapping("/report/month/{month}")
	public Long getTotalBalanceByMonth(@PathVariable("month") int month)
	{
		return accountService.getTotalBalanceByMonth(month) ;
		
	}
	
	@GetMapping("/report/year/{year}")
	public Long getTotalBalanceByYear(@PathVariable("year") int year)
	{
		return accountService.getTotalBalanceByYear(year) ;
		
	}
	
}

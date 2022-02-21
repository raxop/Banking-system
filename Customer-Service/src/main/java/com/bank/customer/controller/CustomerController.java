package com.bank.customer.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.customer.entity.BankInformation;
import com.bank.customer.entity.Customer;
import com.bank.customer.entity.Incentive;
import com.bank.customer.entity.ResponseTemplateVN;
import com.bank.customer.service.CustomerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
		private static final String CUSTOMER_SERVICE="customerService";
	
	    @Autowired
	    private CustomerService customerService;
		
	    @Autowired
	 	private RestTemplate restTemplate;	
	    
		// get all customers handler
		@GetMapping("/viewAllCustomers")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
	    public ResponseEntity<List> getAllCustomers()
	    {
			List customer_list=new ArrayList();
			List<Integer> customerId=customerService.getCustomerId();
			
			for(int i=0;i<customerId.size();i++) {
				Customer customer=customerService.getCustomerById(customerId.get(i));
				BankInformation bank_info= this.restTemplate.getForObject("http://account-service/account/BankInformation/customerid/" +customerId.get(i) , BankInformation.class);
				customer.setBankInformation(bank_info);
				customer_list.add(customer);
			}
			
			if(customer_list.size()<=0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(customer_list);
			
		}
		
		
		//get single customer handler
		@GetMapping("/customerId/{customerId}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId)
		{
			Customer customer= customerService.getCustomerById(customerId);
			BankInformation bank_info= this.restTemplate.getForObject("http://account-service/account/BankInformation/customerid/"+customerId,BankInformation.class);
			customer.setBankInformation(bank_info);
	        ArrayList<Object> bank_cust=new ArrayList<Object>();
			
			bank_cust.add(customer);
			bank_cust.add(bank_info);
			if(bank_cust==null)
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				
			}
			return ResponseEntity.of(Optional.of(customer)) ;
		}
		
		
		//get single customer handler based on name
		@GetMapping("/customerName/{customerName}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> getCustomerByName(@PathVariable("customerName") String name)
		{
		Customer customer= customerService.getCustomerByName(name);
		int customerId=customer.getCid();
		BankInformation bank_info= this.restTemplate.getForObject("http://account-service/account/BankInformation/customerid/"+customerId,BankInformation.class);
		customer.setBankInformation(bank_info);
		if(customer==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
			return ResponseEntity.of(Optional.of(customer)) ;
		}

		//get single customer handler based on accountNumber
		@GetMapping("/customerAccountNumber/{customerAccountNumber}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> getCustomerByAccountNumber(@PathVariable("customerAccountNumber") int accountNumber)
		{
		Customer customer= customerService.getCustomerByAccountNumber(accountNumber);
		int customerId=customer.getCid();
		BankInformation bank_info= this.restTemplate.getForObject("http://account-service/account/BankInformation/customerid/"+customerId,BankInformation.class);
		customer.setBankInformation(bank_info);
		if(customer==null)
		{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
		return ResponseEntity.of(Optional.of(customer)) ;
		}

		
		//add new customer handler
		@PostMapping("/registerCustomer")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer)
		{
		  
		   try
		   {
			   
			   customer.setSignup_status("online");
			   customer=this.customerService.addCustomer(customer);
			   
			   System.out.println(customer);
			   BankInformation bankInformation;
			   bankInformation=(BankInformation) customer.getBankInformation();
			   System.out.println(bankInformation);


			   bankInformation.setCustomerId(customer.getCid());
			   bankInformation.setBalance(0);
				this.restTemplate.postForObject("http://account-service/account/BankInformation", bankInformation,BankInformation.class);
			   return ResponseEntity.of(Optional.of(customer));   
				   
			   
		   }
		   catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
		
		//delete customer handler
		@DeleteMapping("/deleteCustomer/{customerId}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") int customerId)
		{
			try {
			   this.customerService.deleteCustomer(customerId);
				
			   String msg=this.restTemplate.getForObject("http://account-service/account/BankInformation/delete/"+customerId,String.class);
					
			
				
				return new ResponseEntity<String>("Customer Deleted Successfully..!",HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			
		    	
		}
		
		//update customer handler
		@PutMapping("/editCustomer/{customerId}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable("customerId") int customerId)
		{
			try {
				 this.customerService.updateCustomer(customer,customerId);
				 
				 
				 BankInformation bankInformation;
				   bankInformation=(BankInformation) customer.getBankInformation();
				   System.out.println(bankInformation);


				   bankInformation.setCustomerId(customer.getCid());
					this.restTemplate.postForObject("http://account-service/account/BankInformation", bankInformation,BankInformation.class);
				 
				 
		       return ResponseEntity.ok().body(customer);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		  
		}	
		
			
		@PostMapping("/loginCustomer/{accountNumber} {password}")
		public String loginCustomer(@PathVariable("accountNumber") int accountNumber , @PathVariable("password") String password)
		{
			String originalPassword = customerService.getCustomerPasswordById(accountNumber);
			
			if(customerService.getCustomerByAccountNumber(accountNumber)!=null && originalPassword.equals(password)) {
				return "Successfully login with account no: " +accountNumber;
			}
			else {
				return "Customer doesn't exist!! Please sign up first.";
			}
		}
		
		@PostMapping("/addCustomerViaEmployee/employeeId/{employeeId}")
		@CircuitBreaker(name=CUSTOMER_SERVICE,fallbackMethod="customerFallback")
		public ResponseEntity<Customer> addCustomerUsingEmployee(@PathVariable("employeeId") String employeeId,@RequestBody Customer customer)
		{
		  
		   try
		   {
			   
			   customer.setSignup_status(employeeId);
			   customer=this.customerService.addCustomer(customer);
			   
			   System.out.println(customer);
			   BankInformation bankInformation;
			   bankInformation=(BankInformation) customer.getBankInformation();
			   System.out.println(bankInformation);
//			   customer
//			   bankInformation.setAccountNumber(customer.getAccountNumber());
			   bankInformation.setCustomerId(customer.getCid());
			   bankInformation.setBalance(0);
				this.restTemplate.postForObject("http://account-service/account/BankInformation", bankInformation,BankInformation.class);
				this.restTemplate.getForObject("http://payroll-service/payroll/incentive/"+employeeId+"/"+customer.getCid(),Incentive.class);
				
			   return ResponseEntity.of(Optional.of(customer));   
				   
			   
		   }
		   catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
		
		//FallBack Methods
		public ResponseEntity<?> customerFallback(Exception e)
		{
			return new ResponseEntity<String>("Account Service is down",HttpStatus.OK);
		}
}
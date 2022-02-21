package com.bank.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.customer.repository.CustomerRepository;
import com.bank.customer.entity.BankInformation;
import com.bank.customer.entity.Customer;
import com.bank.customer.entity.ResponseTemplateVN;
import com.bank.customer.service.CustomerService;

@Component
@Service
public class CustomerServiceImpl implements CustomerService {
    
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//get all customers
	public List getAllCustomers()
	{
		List customerList= (List) this.customerRepository.getAllCustomers();
		return customerList;
	}
	
	//get single customer by id
			public Customer getCustomerById(int id)
			{  
				Customer customer=null;
				try
		       {
					customer=this.customerRepository.findById(id);
		       }
		       catch(Exception e)
		       {
		    	e.printStackTrace();
		       }
				 
				return customer;
			}
	
			//adding the customer
			public Customer addCustomer(Customer customer)
			{
				Customer result=customerRepository.save(customer);
				return result;
			}
			
			
			//deleting the customer
			public void deleteCustomer(int cid)
			{
			    customerRepository.deleteById(cid);
			}

			
			//updating the customer
			public void updateCustomer(Customer customer, int customerId) {
				// TODO Auto-generated method stub
				Customer ecustomer=this.customerRepository.findById(customerId);
				customer.setSignup_status(ecustomer.getSignup_status());
				customer.setCid(customerId);
				customerRepository.save(customer);
			}
			
			@Override
			public Customer getCustomerByName(String name) {
			return customerRepository.findByName(name);
			}

			@Override
			public Customer getCustomerByAccountNumber(int AccountNumber) {
			return customerRepository.findByAccountNumber(AccountNumber);
			}
	      
			
			
			@Override
			public String getCustomerPasswordById(int accountNumber) {
				return customerRepository.findPasswordById(accountNumber);
			}

			@Override
			public List<Integer> getCustomerId() {
				// TODO Auto-generated method stub
				return customerRepository.getCustomerId();
			}
			
}
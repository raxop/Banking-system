package com.bank.customer.service;

import java.util.List;

import com.bank.customer.entity.Customer;

public interface CustomerService {

	List getAllCustomers();
	Customer getCustomerById(int id);
	Customer addCustomer(Customer c);
	void deleteCustomer(int cid);
	void updateCustomer(Customer customer, int customerId);
	String getCustomerPasswordById(int accountNumber);
	Customer getCustomerByName(String name);
	Customer getCustomerByAccountNumber(int accountNumber);
	List<Integer> getCustomerId();

}
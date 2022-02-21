package com.bank.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
	public Customer findById(int id);
	
	@Query(value = "select customer_password from customer c where account_number = :accountNumber" , nativeQuery=true)
	public String findPasswordById(@Param("accountNumber") int accountNumber);
    
	@Query("select c from Customer c where c.name=:name")
	public Customer findByName(@Param("name") String name);

	@Query("select c from Customer c where c.accountNumber=:accountNumber")
	public Customer findByAccountNumber(@Param("accountNumber") int accountNumber);

	@Query(value="select c.customer_id,customer_name,customer_aadhar_card_no,c.account_number,customer_address ,customer_contact_no,customer_dob ,customer_email_id ,customer_password,customer_signup_status,account_type,balance from customer_service.customer inner join account_service.bank_information b where c.customer_id=b.customer_id and c.account_number=b.account_number ",nativeQuery = true)
	public List getAllCustomers();
	
	@Query(value="select customer_id from Customer" , nativeQuery=true)
	public List<Integer> getCustomerId();

}
package com.employee.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.employee.entity.AttendanceEntity;
import com.employee.entity.Customer;
import com.employee.entity.EmployeeEntity;
import com.employee.entity.Transaction;
import com.employee.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmployeeService employeeService;
	
	private static final String EMPLOYEE_SERVICE="employeeService";
	
//	Add employee into system
	@PostMapping("/registerEmployee")
	public EmployeeEntity saveEmployeeDetails(@RequestBody EmployeeEntity employeeEntity) {
		return employeeService.saveEmployeeDetails(employeeEntity);
	}
	
//	Get all employee
	@GetMapping("/allEmployees")
	public List<EmployeeEntity> getEmployeeDetails() {
		return employeeService.getEmployeeDetails();
	}
	
//	Get employee by id
	@GetMapping("/EmployeeId/{employeeId}")
	public EmployeeEntity getEmployeeDetail(@PathVariable("employeeId") int employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}
	
//	Delete employee
	@DeleteMapping("/deleteEmployee/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") int employeeId) {
		employeeService.deleteEmployee(employeeId);
		return "Deleted successfully";
	}
	
//	Login of employee
	@PostMapping("/loginEmployee/{employeeId} {password}")
	public String loginEmployee(@PathVariable("employeeId") int employeeId , @PathVariable("password") String password) {
		String originalPassword = employeeService.getEmployeePasswordById(employeeId);
		
		if(employeeService.getEmployeeById(employeeId)!=null && originalPassword.equals(password)) {
			if(employeeService.getAttendanceById(employeeId)==null) {
				AttendanceEntity attendance = new AttendanceEntity(employeeId , java.time.LocalDate.now());
				employeeService.saveAttendaceDetails(attendance);
			}
			else {
				
			}
			
			return "Successfully login with id no: " +employeeId;
		}
		
		else {
			return "Employee doesn't exist!! Please register first.";
		}
		
	}
	
//	Deposit amount into any customer’s account
	@PostMapping("/customer/deposit")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="employeeFallback")
	public ResponseEntity<String> depositAmount(@RequestBody Transaction transaction) {
		this.restTemplate.postForObject("http://account-service/account/Transaction/deposit" , transaction , Transaction.class);
		return new ResponseEntity<String>("Balance deposited to acocunt " +transaction.getAmount() , HttpStatus.OK);
	}
	
//	Account status of customer 1
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="employeeFallbackAccountStatus")
	@GetMapping("/customerAccountStatus/{accountNumber}/year {year}")
	public List<Transaction> accountStatusByYear(@PathVariable("accountNumber") String accountNumber , @PathVariable("year") int year) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/" +accountNumber +"/year/" +year , List.class);
		return transaction;
	}
	
//	Account status of customer 2
	@GetMapping("/customerAccountStatus/{accountNumber}/month {month}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="employeeFallbackAccountStatus")
	public List<Transaction> accountStatusByMonth(@PathVariable("accountNumber") String accountNumber , @PathVariable("month") int month) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/" +accountNumber +"/month/" +month , List.class);
		return transaction;
	}
	
//	Account status of customer 3
	@GetMapping("/customerAccountStatus/{accountNumber}/date {date1} {date2}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="employeeFallbackAccountStatus")
	public List<Transaction> accountStatusBetweenTwoDates(@PathVariable("accountNumber") String accountNumber , @PathVariable("date1") String sdate1 , @PathVariable("date2") String sdate2) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/" +accountNumber +"/date/" +sdate1 +"/" +sdate2 , List.class);
		return transaction;
	}
	
//	Open the customer account
	@PostMapping("/customerAccount/{employeeId}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="customerFallback")
	public ResponseEntity<Customer> createCustomerAccount(@PathVariable("employeeId") int employeeId,@RequestBody Customer customer) {
		return new ResponseEntity<Customer>(this.restTemplate.postForObject("http://Customer-Service/customers/addCustomerViaEmployee/employeeId/" +employeeId , customer , Customer.class),HttpStatus.OK);
	}
	
//	Search a customer in the system
//	By name
	@GetMapping("/customerName/{customerName}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="customerFallback")
	public ResponseEntity<Customer> getCustomerByName(@PathVariable("customerName") String customerName) {
		return new ResponseEntity<Customer>(this.restTemplate.getForObject("http://Customer-Service/customers/customerName/"+customerName , Customer.class),HttpStatus.OK);
	}
	
//	By account number
	@GetMapping("/customerAccountNumber/{accountNumber}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE , fallbackMethod="customerFallback")
	public ResponseEntity<Customer> getCustomerByAccountNumber(@PathVariable("accountNumber") int accountNumber) {
		return new ResponseEntity<Customer>(this.restTemplate.getForObject("http://Customer-Service/customers/customerAccountNumber/" +accountNumber , Customer.class) , HttpStatus.OK);
	}
	
	
//	Get employee attendance for the current date
	@RequestMapping("/employeeAttendance/{employeeId}")
	public AttendanceEntity getAttendanceDetails(@PathVariable("employeeId") int employeeId) {
		return employeeService.getAttendanceById(employeeId);
	}
	
//	Get present days of employee
	@GetMapping("/getPresentDays/{employeeId} {month} {year}")
	public int getPresentDays(@PathVariable("employeeId") int employeeId , @PathVariable("month") int month , @PathVariable("year") int year) {
		return employeeService.getPresentDays(employeeId ,  month , year);
	}
	
//	Get Total Employee
	@GetMapping("/getTotalEmployee")
	public List<Integer> getTotalEmployee(){
		return employeeService.getTotalEmployee();
	}
	
	
//	Fallback Methods
	public ResponseEntity<?> employeeFallback(Exception e){
		return new ResponseEntity<String>("Account Service is down" , HttpStatus.OK);
	}
	
	public List employeeFallbackAccountStatus(Exception e){
		List list=new ArrayList();
		list.add("Account Service is down");
		return list;
	}
	
	public ResponseEntity<?> customerFallback(Exception e){
		return new ResponseEntity<String>("Customer Service is down" , HttpStatus.OK);
	}
	
}

package com.bank.employee.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.bank.employee.entity.Attendance;
import com.bank.employee.entity.Customer;
import com.bank.employee.entity.Employee;
import com.bank.employee.entity.Transaction;
import com.bank.employee.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	private static final String EMPLOYEE_SERVICE="employeeService";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmployeeService employeeService;
	
//	Add employee into system
	@PostMapping("/registerEmployee")
	@ResponseBody
	public Employee saveEmployeeDetails(@RequestBody Employee employeeEntity) {
		return employeeService.saveEmployeeDetails(employeeEntity);
		
	}
	
//	Get all employee
	@RequestMapping("/allEmployees")
	public List<Employee> getEmployeeDetails() {
		return employeeService.getEmployeeDetails();
	}
	
//	Get employee by id
	@RequestMapping("/employeeId/{employeeId}")
	public Employee getEmployeeDetail(@PathVariable("employeeId") int employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}
	
//	Delete employee
	@DeleteMapping("/deleteEmployee/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") int employeeId)
	{
		employeeService.deleteEmployee(employeeId);
		return "Deleted successfully";
	}
	
//	Login of employee
	@RequestMapping("/loginEmployee/{employeeId} {password}")
	public String loginEmployee(@PathVariable("employeeId") int employeeId , @PathVariable("password") String password)
	{
		String originalPassword = employeeService.getEmployeePasswordById(employeeId);
		
		if(employeeService.getEmployeeById(employeeId)!=null && originalPassword.equals(password)) {
			if(employeeService.getAttendanceById(employeeId)==null) {
			Attendance attendance = new Attendance(employeeId , java.time.LocalDate.now());
			employeeService.saveAttendaceDetails(attendance);
			}
			else {
				
			}
			return "Successfully login with id no: " +employeeId;
		}
		else {
			return "Employee doesn't exist!! Please sign up first.";
		}
	}
	
//	Get employee attendance
	@RequestMapping("/employeeAttendance/{employeeId}")
	public Attendance getAttendanceDetails(@PathVariable("employeeId") int employeeId) {
		return employeeService.getAttendanceById(employeeId);
	}
	
//	Deposit amount into any customer’s account
	@PostMapping("/customer/deposit")
	@ResponseBody
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="employeeFallback")
	public ResponseEntity<String> depositAmount(@RequestBody Transaction transaction) {
		Transaction transaction1 = new Transaction();
		this.restTemplate.postForObject("http://account-service/account/Transaction/deposit" , transaction , Transaction.class);
		return new ResponseEntity<String>("Balance deposited to acocunt "+transaction.getAmount(),HttpStatus.OK);
	}
	
	
	
//	Account status of customer 1
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="employeeFallbackAccountStatus")
	@GetMapping("/customerAccountStatus/{accountNumber}/year {year}")
	public List<Transaction> accountStatus(@PathVariable("accountNumber") String accountNumber , @PathVariable("year") int year) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/"+accountNumber+"/year/"+year , List.class);
		return transaction;
	}
	
//	Account status of customer 2

	@GetMapping("/customerAccountStatus/{accountNumber}/month {month}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="employeeFallbackAccountStatus")
	public List<Transaction> accountStatus1(@PathVariable("accountNumber") String accountNumber,@PathVariable("month") int month) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/"+accountNumber+"/month/"+month , List.class);
		return transaction;
	}
	
//	Account status of customer 3
	@GetMapping("/customerAccountStatus/{accountNumber}/date {date1} {date2}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="employeeFallbackAccountStatus")
	public List<Transaction> accountStatus2(@PathVariable("accountNumber") String accountNumber,@PathVariable("date1") String sdate1,@PathVariable("date2") String sdate2) {
		List<Transaction> transaction = this.restTemplate.getForObject("http://account-service/account/Transaction/accountstatus/"+accountNumber+"/date/"+sdate1+"/"+sdate2 , List.class);
		return transaction;
	}
	
//	open the customer account
	@PostMapping("/customerAccount/{employeeId}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="customerFallback")
	public ResponseEntity<Customer> createCustomerAccount(@PathVariable("employeeId") int employeeId,@RequestBody Customer customer) {
		//Customer customer = this.restTemplate.postForObject("http://Customer-Service/addCustomerViaEmployee/employeeId/"+employeeId , customer , Customer.class);
		return new ResponseEntity<Customer>(this.restTemplate.postForObject("http://customer-Service/customers/addCustomerViaEmployee/employeeId/"+employeeId , customer , Customer.class),HttpStatus.OK);
	}
	
//	Search a customer in the system
//	By name
	@GetMapping("/customerName/{customerName}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="customerFallback")
	public ResponseEntity<Customer> getCustomerByName(@PathVariable("customerName") String customerName) {
		//Customer customer = this.restTemplate.getForObject("http://Customer-Service/customers/customername/{customerName}" , Customer.class);
		return new ResponseEntity<Customer>(this.restTemplate.getForObject("http://customer-service/customers/customername/"+customerName , Customer.class),HttpStatus.OK);
	}
	
//	By account number
	@GetMapping("/customerAccountNumber/{accountNumber}")
	@CircuitBreaker(name=EMPLOYEE_SERVICE,fallbackMethod="customerFallback")
	public ResponseEntity<Customer> getCustomerByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
		//Customer customer = this.restTemplate.getForObject("http://Customer-Service/customers/customeraccountnumber/{customerAccountNumber}" , Customer.class);
		return new ResponseEntity<Customer>(this.restTemplate.getForObject("http://customer-service/customers/customerAccountNumber/"+accountNumber , Customer.class),HttpStatus.OK);
	}
	
	@RequestMapping("/getPresentDays/{employeeId} {month} {year}")
	public int getPresentDays(@PathVariable("employeeId") int employeeId,@PathVariable("month") int month,@PathVariable("year") int year)
	{
		return employeeService.getPresentDays(employeeId,month,year);
	}
	
	
	//get Total Employee
	@RequestMapping("/getTotalEmployee")
	public List<Integer> getTotalEmployee()
	{
		return employeeService.getTotalEmployee();
	}
	
	//Fallback Methods
	
	public ResponseEntity<?> employeeFallback(Exception e)
	{
		return new ResponseEntity<String>("Account Service is down",HttpStatus.OK);
	}
	public List employeeFallbackAccountStatus(Exception e)
	{
		List list=new ArrayList();
		list.add("Account Service is down");
		return list;
	}
	public ResponseEntity<?> customerFallback(Exception e)
	{
		return new ResponseEntity<String>("Customer Service is down",HttpStatus.OK);
	}
	
		
}

package com.bank.payroll.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.payroll.entity.Incentive;
import com.bank.payroll.entity.EmployeeEntity;
import com.bank.payroll.entity.Payroll;
import com.bank.payroll.service.PayrollService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

	private static final String PAYROLL_SERVICE = "payrollService";
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/incentive/{employeeId}/{customerId}")
	public void addIncentive(@PathVariable("employeeId") int employeeId,@PathVariable("customerId") int customerId )
	{
		Incentive incentive=new Incentive();
		incentive.setEmployeeId(employeeId);
		incentive.setCustomerId(customerId);
		incentive.setDate(java.time.LocalDate.now());
		payrollService.addIncentive(incentive);
	}
	
	@GetMapping("/addPayroll")
	@CircuitBreaker(name=PAYROLL_SERVICE,fallbackMethod = "payrollFallback")
	public ResponseEntity<String> addPayroll() {
		LocalDate date=java.time.LocalDate.now();
		int month=date.getMonthValue();
		int year=date.getYear();
		List<Integer> list=this.restTemplate.getForObject("http://employee-service/employees/getTotalEmployee" , List.class);
		//List<Integer> list=payrollService.getTotalEmployee();
		
		for(int i=0;i<list.size();i++) {
			
			Payroll payroll=new Payroll();
			payroll.setEmployeeId(list.get(i));
			Integer attendance = this.restTemplate.getForObject("http://employee-service/employees/getPresentDays/" +payroll.getEmployeeId() +" " +month +" " +year , Integer.class);
			payroll.setMonth(month);
			payroll.setYear(year);
			payroll.setCustomerhandled(payrollService.countIncentive(payroll.getEmployeeId(), month, year));
			payroll.setIncentive(payroll.getCustomerhandled()*100);
			payroll.setPresentDays(attendance);
			payroll.setSalary(attendance*4000);
			payroll.setTotal(payroll.getSalary()+payroll.getIncentive());
			payrollService.addPayroll(payroll);
			
		}
		return new ResponseEntity<String>("Data added successfully" , HttpStatus.OK);
		
	}
	
	@GetMapping("/getPayrollDetail/{month}/{year}")
	public List<Payroll> getPayrollDetail(@PathVariable("month") int month , @PathVariable("year") int year){
		return payrollService.getPayrollDetail(month, year);
	}
	
//	Fallback method
	public ResponseEntity<?> payrollFallback(Exception e){
		return new ResponseEntity<String>("Employee Service is down" , HttpStatus.OK);
	}
}

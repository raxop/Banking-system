package com.bank.report.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.report.entity.Customer;
import com.bank.report.service.ReportService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/report")
public class ReportController {

	private static final String REPORT_SERVICE="reportService";
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/month/{month}")
	@CircuitBreaker(name=REPORT_SERVICE,fallbackMethod="reportFallback")
	public List getReportByMonth(@PathVariable("month") int month)
	{
		ArrayList<Object> report=new ArrayList<Object>();
		Long totalBalance=this.restTemplate.getForObject("http://account-service/account/report/month/"+month, Long.class);
		//List<Customer> customerdetails=this.restTemplate.getForObject("http://customer-service/customers/customerReport/month/"+month, List.class);
		List<Integer> customerId=this.restTemplate.getForObject("http://account-service/account/report/month/customerId/"+month,List.class);
		
		for(int i=0;i<customerId.size();i++)
		{
			Customer customerdetails=this.restTemplate.getForObject("http://customer-service/customers/customerId/"+customerId.get(i), Customer.class);
			report.add(customerdetails);
		}
		report.add("Total Monthly Balance:"+totalBalance);
		return report;
	}
	
	@GetMapping("/year/{year}")
	@CircuitBreaker(name=REPORT_SERVICE,fallbackMethod="reportFallback")
	public List getReportByYear(@PathVariable("year") int year)
	{
		Long totalBalance=this.restTemplate.getForObject("http://account-service/account/report/year/"+year, Long.class);
		//List<Customer> customerdetails=this.restTemplate.getForObject("http://customer-service/customers/customerReport/year/"+year, List.class);
		List<Integer> customerId=this.restTemplate.getForObject("http://account-service/account/report/year/customerId/"+year,List.class);
		System.out.println(customerId);
		ArrayList<Object> report=new ArrayList<Object>();
		for(int i=0;i<customerId.size();i++)
		{
			Customer customerdetails=this.restTemplate.getForObject("http://customer-service/customers/customerId/"+customerId.get(i), Customer.class);
			report.add(customerdetails);
		}
		report.add("Total Yearly Balance:"+totalBalance);
		

		return report;
	}
	
	public List reportFallback(Exception e)
	{
		List<String> list=new ArrayList();
		list.add("Customer Service is down");
		return list;
	}
	
}
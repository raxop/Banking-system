package com.bank.payroll.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.payroll.entity.EmployeeEntity;
import com.bank.payroll.entity.Incentive;
import com.bank.payroll.entity.Payroll;

public interface PayrollService {
	
	void addPayroll(Payroll payroll);
	void addIncentive(Incentive incentive);

	int countIncentive(int employee_id,int month,int year);
	
	List<Payroll> getPayrollDetail(int month , int year);
	
}

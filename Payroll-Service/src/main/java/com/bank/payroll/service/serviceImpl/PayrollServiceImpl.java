package com.bank.payroll.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.payroll.entity.EmployeeEntity;
import com.bank.payroll.entity.Incentive;
import com.bank.payroll.entity.Payroll;
import com.bank.payroll.repository.IncentiveRepository;
import com.bank.payroll.repository.PayrollRepository;
import com.bank.payroll.service.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService{
	
	@Autowired
	private PayrollRepository payrollRepository;
	
	@Autowired
	private IncentiveRepository incentiveRepository;

	@Override
	public void addPayroll(Payroll payroll) {
		payrollRepository.save(payroll);
		
	}

	@Override
	public void addIncentive(Incentive incentive) {
		incentiveRepository.save(incentive);
		
	}

	
	@Override
	public int countIncentive(int employee_id,int month,int year) {
		return payrollRepository.countIncentive(employee_id,month,year);
	}

	@Override
	public List<Payroll> getPayrollDetail(int month, int year) {
		return payrollRepository.getPayrollDetail(month, year);
	}
	
	
}

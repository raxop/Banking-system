package com.bank.employee.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.employee.entity.Attendance;
import com.bank.employee.entity.Employee;
import com.bank.employee.repository.AttendanceRepository;
import com.bank.employee.repository.EmployeeRepository;
import com.bank.employee.service.EmployeeService;



@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@Autowired
	private AttendanceRepository attendanceRepository;

	public EmployeeServiceImpl() {
		super();
	}

	@Override
	public Employee saveEmployeeDetails(Employee employeeEntity) {
		return employeeRepository.save(employeeEntity);
	}

	@Override
	public List<Employee> getEmployeeDetails() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Optional<Employee> employeeEntity = employeeRepository.findById(employeeId);
		if(employeeEntity.isPresent())
		{
			return employeeEntity.get();
		}
		else
		{
			return null;
		}
	}

	@Override
	public void deleteEmployee(int employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public String getEmployeePasswordById(int employeeId) {
		return employeeRepository.findPasswordById(employeeId);
	}

	
	@Override
	public Employee updateEmployeeDetails(Employee employeeEntity, int employeeId) {
		return null;
	}
	
	
	@Override
	public Attendance getAttendanceById(int employeeId) {
		return attendanceRepository.findAttendanceById(employeeId);
	}

	@Override
	public void saveAttendaceDetails(Attendance attendance) {
		attendanceRepository.save(attendance);
	}

	@Override
	public int getPresentDays(int employeeId, int month, int year) {
		// TODO Auto-generated method stub
		return  attendanceRepository.getPresentDays(employeeId,month,year);
	}

	@Override
	public List<Integer> getTotalEmployee() {
		// TODO Auto-generated method stub
		return employeeRepository.getTotalEmployee();
	}

}

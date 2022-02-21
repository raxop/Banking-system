package com.bank.employee.service;

import java.util.List;

import com.bank.employee.entity.Attendance;
import com.bank.employee.entity.Employee;


public interface EmployeeService {

	//Employee Methods
	Employee saveEmployeeDetails(Employee employeeEntity);
	List<Employee> getEmployeeDetails();	
	Employee getEmployeeById(int employeeId);
	Employee updateEmployeeDetails(Employee employeeEntity,int employeeId);
	void deleteEmployee(int employeeId);
	String getEmployeePasswordById(int employeeId);
	
	
	
	//Attendance Methods
	void saveAttendaceDetails(Attendance attendance);
	Attendance getAttendanceById(int employeeId);
	int getPresentDays(int employeeId, int month, int year);
	List<Integer> getTotalEmployee();
}

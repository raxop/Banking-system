package com.employee.service;

import java.util.*;
import com.employee.entity.*;

public interface EmployeeService {
	
//	Employee Methods
	EmployeeEntity saveEmployeeDetails(EmployeeEntity employeeEntity);
	List<EmployeeEntity> getEmployeeDetails();	
	EmployeeEntity getEmployeeById(int employeeId);
	void deleteEmployee(int employeeId);
	String getEmployeePasswordById(int employeeId);
	
//	Attendance Methods
	void saveAttendaceDetails(AttendanceEntity attendanceEntity);
	AttendanceEntity getAttendanceById(int employeeId);
	int getPresentDays(int employeeId , int month , int year);
	List<Integer> getTotalEmployee();
	
}
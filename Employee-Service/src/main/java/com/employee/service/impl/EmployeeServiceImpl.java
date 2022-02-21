package com.employee.service.impl;

import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.AttendanceEntity;
import com.employee.entity.EmployeeEntity;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

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
	public EmployeeEntity saveEmployeeDetails(EmployeeEntity employeeEntity) {
		return employeeRepository.save(employeeEntity);
	}

	@Override
	public List<EmployeeEntity> getEmployeeDetails() {
		return employeeRepository.findAll();
	}

	@Override
	public EmployeeEntity getEmployeeById(int employeeId) {
		Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
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
	public AttendanceEntity getAttendanceById(int employeeId) {
		return attendanceRepository.findAttendanceById(employeeId);
	}

	@Override
	public void saveAttendaceDetails(AttendanceEntity attendanceEntity) {
		attendanceRepository.save(attendanceEntity);
	}

	@Override
	public int getPresentDays(int employeeId , int month , int year) {
		return attendanceRepository.getPresentDays(employeeId , month , year);
	}
	
	@Override
	public List<Integer> getTotalEmployee() {
		return employeeRepository.getTotalEmployee();
	}
	
}
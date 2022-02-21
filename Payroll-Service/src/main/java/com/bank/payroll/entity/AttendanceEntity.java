package com.bank.payroll.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Attendance")
public class AttendanceEntity {

	@Id
	int employeeId;
	
	LocalDate date;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AttendanceEntity(int employeeId, LocalDate date) {
		super();
		this.employeeId = employeeId;
		this.date = date;
	}

	public AttendanceEntity() {
		super();
	}

	@Override
	public String toString() {
		return "AttendanceEntity [employeeId=" + employeeId + ", date=" + date + "]";
	}
	
}

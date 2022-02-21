package com.bank.employee.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Attendance")
public class Attendance {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	int id;
	
	@Column(name = "employeeId")
	int employeeId;
	
	@Column(name = "date")
	LocalDate date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Attendance(int employeeId, LocalDate date) {
		super();
		this.employeeId = employeeId;
		this.date = date;
	}

	public Attendance() {
		super();
	}

	@Override
	public String toString() {
		return "AttendanceEntity [id=" + id + ", employeeId=" + employeeId + ", date=" + date + "]";
	}

	
	
}

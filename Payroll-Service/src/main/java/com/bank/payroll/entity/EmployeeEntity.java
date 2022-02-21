package com.bank.payroll.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "Employee")
public class EmployeeEntity {
	
	@Id
	@Column(name="employeeId")
	private int employeeId;
	
	@Column(name="employeeName")
	private String employeeName;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="department")
	private String department;
	
	@Column(name="dateOfBirth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="contactNumber")
	private Long contactNumber;
	
	@Column(name="salary")
	private Float salary;
	
	@Column(name="address")
	private String address;
	
	@Column(name="password")
	private String password;
	
//	Super class constructor
	public EmployeeEntity() {
		super();
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [employeeId=" + employeeId + ", employeeName=" + employeeName + ", designation="
				+ designation + ", department=" + department + ", dateOfBirth=" + dateOfBirth + ", emailId=" + emailId
				+ ", contactNumber=" + contactNumber + ", salary=" + salary + ", address=" + address + ", password="
				+ password + "]";
	}

}

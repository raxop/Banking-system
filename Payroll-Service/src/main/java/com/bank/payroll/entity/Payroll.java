package com.bank.payroll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Payroll")
public class Payroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	int id;
	
	@Column(name = "employeeId")
	int employeeId;
	
	@Column(name = "month")
	int month;
	
	@Column(name = "year")
	int year;
	
	@Column(name = "presentDays")
	int presentDays;
	
	@Column(name = "customerHandled")
	int customerhandled;
	
	@Column(name = "salary")
	int salary;
	
	@Column(name = "incentive")
	int incentive;
	
	@Column(name = "total")
	int total;

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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}

	public int getCustomerhandled() {
		return customerhandled;
	}

	public void setCustomerhandled(int customerhandled) {
		this.customerhandled = customerhandled;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getIncentive() {
		return incentive;
	}

	public void setIncentive(int incentive) {
		this.incentive = incentive;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Payroll(int id, int employeeId, int month, int year, int presentDays, int customerhandled, int salary,
			int incentive, int total) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.month = month;
		this.year = year;
		this.presentDays = presentDays;
		this.customerhandled = customerhandled;
		this.salary = salary;
		this.incentive = incentive;
		this.total = total;
	}

	public Payroll() {
		super();
	}

	@Override
	public String toString() {
		return "Payroll [id=" + id + ", employeeId=" + employeeId + ", month=" + month + ", year=" + year
				+ ", presentDays=" + presentDays + ", customerhandled=" + customerhandled + ", salary=" + salary
				+ ", incentive=" + incentive + ", total=" + total + "]";
	}
	
}

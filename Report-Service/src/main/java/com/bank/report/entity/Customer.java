package com.bank.report.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_Id")
	private int cid;
	
	@Column(name="customer_Name")
	private String name;
	
	@Column(name="customer_Address")
	private String address;
	
	@Column(name="customer_EmailId")
	private String emailId;
	
	@Column(name="customer_Dob")
	@Temporal(TemporalType.DATE) 
	private Date dob;
	
	@Column(name="customer_ContactNo",length=10)
	private long contact_no;
	
	@Column(name="customer_AadharCardNo",length=12)
	private long aadharcard_no;
	
	@Column(name="customer_Password")
	private String password;
	
	@Column(name = "customer_signup_status")
	private String signup_status;
	
    private int accountNumber;
    
    @OneToOne(mappedBy="customer")
    private BankInformation bankInformation;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getContact_no() {
		return contact_no;
	}

	public void setContact_no(long contact_no) {
		this.contact_no = contact_no;
	}

	public long getAadharcard_no() {
		return aadharcard_no;
	}

	public void setAadharcard_no(long aadharcard_no) {
		this.aadharcard_no = aadharcard_no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignup_status() {
		return signup_status;
	}

	public void setSignup_status(String signup_status) {
		this.signup_status = signup_status;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

//	public BankInformation getBankInformation() {
//		return bankInformation;
//	}
//
//	public void setBankInformation(BankInformation bankInformation) {
//		this.bankInformation = bankInformation;
//	}

	
	
	
	

}
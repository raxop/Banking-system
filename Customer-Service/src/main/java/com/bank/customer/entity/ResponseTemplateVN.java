package com.bank.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVN {
     
	private Customer customer;
	private BankInformation bankInformation;
	
}

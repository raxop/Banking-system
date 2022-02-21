package com.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.account.entity.BankInformation;

@Repository
@Transactional
public interface BankInformationRepository  extends JpaRepository<BankInformation, Integer> {
	
	@Query(value="select * from bank_information where customer_id=:customerId",nativeQuery=true)
	BankInformation getBankDetailsByCustomerId(@Param("customerId") int customerId);

	@Modifying 
	@Query(value="delete from bank_information where customer_id=:customerId ",nativeQuery = true)
	void deleteByCustomerId(@Param("customerId") int customerId);
	
}

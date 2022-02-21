package com.bank.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<com.bank.report.entity.Transaction, Long> {

	@Query("SELECT t, b FROM  Transaction t, BankInformation b  WHERE t.customerId = b.customerId  and t.customerId=:customerid")
	List<Object[]> getReport(@Param("customerid") String customerid);
	
}
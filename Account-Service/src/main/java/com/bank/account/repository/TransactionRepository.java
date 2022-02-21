package com.bank.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.account.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("select t from Transaction t where t.accountNumber=:accountNumber and year(t.date)=:year")
	List<Transaction> findByDate(@Param("accountNumber") int accountNumber,@Param("year") int year);
	
	@Query("select t from Transaction t where t.accountNumber=:accountNumber and month(t.date)=:month ")
	List<Transaction> findByMonth(@Param("accountNumber") int accountNumber,@Param("month") int month);

	@Query("select t from Transaction t where t.accountNumber=:accountNumber and t.date between :date1 and :date2 ")
	List<Transaction> findByTwoDates(@Param("accountNumber") int accountNumber,@Param("date1") Date date1,@Param("date2") Date date2);

	@Query("select sum(t.newBalance) from Transaction t where month(t.date)=?1 and t.transactionId in(select max(t.transactionId) from Transaction t group by t.accountNumber)")
	Long getTotalBalanceByMonth(int month);
	
	@Query("select sum(t.newBalance) from Transaction t where year(t.date)=?1 and t.transactionId in(select max(t.transactionId) from Transaction t group by t.accountNumber)")
	Long getTotalBalanceByYear(int year);

	@Query(value="select DISTINCT(customer_id) from transaction  t where month(t.date)=:month ",nativeQuery=true)
	List<Integer> getcustomerIdByMonth(@Param("month") int month);

	@Query(value="select DISTINCT(customer_id) from transaction  t where year(t.date)=:year ",nativeQuery=true)
	List<Integer> getcustomerIdByYear(@Param("year") int year);
	
}
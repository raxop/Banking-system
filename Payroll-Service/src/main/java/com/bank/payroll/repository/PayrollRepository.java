package com.bank.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.payroll.entity.EmployeeEntity;
import com.bank.payroll.entity.Payroll;

import lombok.Data;

@EnableJpaRepositories
@Repository
public interface PayrollRepository extends JpaRepository<Payroll , Integer> {
	
	@Query(value="select count(employee_id) from incentive where employee_id= :employee_id and month(date)=:month and year(date)=:year" , nativeQuery = true)
	int countIncentive(@Param("employee_id") int employee_id,@Param("month") int month,@Param("year") int year);
	
	@Query(value="select * from payroll where month = :month and year = :year",nativeQuery=true)
	List<Payroll> getPayrollDetail(@Param("month")int month , @Param("year") int year);

}

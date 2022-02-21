package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.entity.EmployeeEntity;

@EnableJpaRepositories
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Integer>{
	
	@Query(value = "select password from employee e where e.employee_id = :employeeId" , nativeQuery=true)
	public String findPasswordById(@Param("employeeId") int employeeId);
	
	@Query(value="select employee_id from employee_service.employee",nativeQuery=true)
	public List<Integer> getTotalEmployee();
}
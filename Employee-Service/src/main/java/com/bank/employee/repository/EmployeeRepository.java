package com.bank.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Integer>{


	@Query(value = "select password from employee e where e.employee_id = :employeeId" , nativeQuery=true)
	public String findPasswordById(@Param("employeeId") int employeeId);


	@Query(value="select employee_id from employee_service.employee",nativeQuery=true)
	public List<Integer> getTotalEmployee();
}
package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.entity.AttendanceEntity;

@EnableJpaRepositories
@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity , Integer>{
	
	@Query(value = "select * from Attendance a where a.employee_id = :employeeId and a.date = curdate()" , nativeQuery = true)
	public AttendanceEntity findAttendanceById(@Param("employeeId") int employeeId);
	
	@Query(value = "select count(employee_id) from Attendance where employee_id = :employeeId and month(date) = :month and year(date) = :year" , nativeQuery = true)
	public int getPresentDays(@Param("employeeId") int employeeId , @Param("month") int month , @Param("year") int year);
	
}
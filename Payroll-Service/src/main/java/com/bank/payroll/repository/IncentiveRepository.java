package com.bank.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.payroll.entity.Incentive;
import com.bank.payroll.entity.Payroll;

public interface IncentiveRepository extends JpaRepository<Incentive , Integer> {

}

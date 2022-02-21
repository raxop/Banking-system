package com.bank.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.report.repository.ReportRepository;
import com.bank.report.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	
	@Autowired
	private ReportRepository reportRepository;
	
	public ReportServiceImpl() {
		super();
	}

	public ReportServiceImpl(ReportRepository reportRepository) {
		super();
		this.reportRepository = reportRepository;
	}

	@Override
	public List<Object[]> getReport(String customerid) {
		// TODO Auto-generated method stub
		return reportRepository.getReport(customerid);
	}

}

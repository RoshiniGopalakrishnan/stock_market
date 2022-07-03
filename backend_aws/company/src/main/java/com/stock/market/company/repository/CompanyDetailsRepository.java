package com.stock.market.company.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.stock.market.company.entity.CompanyDetails;

@EnableScan
public interface CompanyDetailsRepository extends CrudRepository<CompanyDetails, String> {
	void deleteByCompanyCode(String companyCode);
	CompanyDetails findByCompanyCode(String companyCode);

}

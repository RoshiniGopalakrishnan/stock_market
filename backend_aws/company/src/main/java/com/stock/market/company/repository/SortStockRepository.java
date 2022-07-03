package com.stock.market.company.repository;

import java.util.Date;
import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.stock.market.company.entity.Price;

@EnableScan
public interface SortStockRepository extends PagingAndSortingRepository<Price, String>{

	List<Price> findAllByOrderByCreationDateDesc();
	
	List<Price>  findByCompanyCodeOrderByCreationDateDesc(String companyCode);
	List<Price> findAllByCompanyCode(String companyCode);
	
	void deleteByIdAndCreationDate(String id,Date creationDate);
	
}

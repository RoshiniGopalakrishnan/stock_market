package com.stock.market.company.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.stock.market.company.entity.Price;

@EnableScanCount
public interface StockPriceRepository extends CrudRepository<Price, String>{
	List<Price> findAllByOrderByCreationDateDesc();
	void deleteAllByCompanyCode(String companyCode);
	List<Price> findByCompanyCodeOrderByCreationDateDesc(String companyCode);
	Iterable<Price> findAll(Sort descending);
}

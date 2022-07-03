package com.stock.market.stockprice.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.stock.market.stockprice.entity.Price;

@EnableScan
public interface SortStockRepository extends PagingAndSortingRepository<Price, String> {

	List<Price> findAllByOrderByCreationDateDesc();

	List<Price> findByCompanyCodeOrderByCreationDateDesc(String companyCode);

	List<Price> findAllByCompanyCode(String companyCode);

}

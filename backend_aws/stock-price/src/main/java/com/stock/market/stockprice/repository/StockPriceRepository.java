package com.stock.market.stockprice.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.stock.market.stockprice.entity.Price;
/**
 * @author Roshini
 *
 */
@EnableScan
public interface StockPriceRepository extends CrudRepository<Price, String> {

//	List<Price> findByCompanyCodeAndCreationDateBetween(String companyCode,Date startDate, Date endDate);
}

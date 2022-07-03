package com.stock.market.stockprice.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.stock.market.stockprice.entity.UserDetail;
/**
 * @author Roshini
 *
 */
@EnableScan
public interface UserDetailRepository extends CrudRepository<UserDetail, String>{

	UserDetail findByUsername(String username);
}

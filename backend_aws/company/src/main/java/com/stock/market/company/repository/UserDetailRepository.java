package com.stock.market.company.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.stock.market.company.entity.UserDetail;

@EnableScan
public interface UserDetailRepository extends CrudRepository<UserDetail, String>{

	UserDetail findByUsername(String username);
}

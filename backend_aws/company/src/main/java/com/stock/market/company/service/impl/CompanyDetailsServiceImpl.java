package com.stock.market.company.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.market.company.dto.CompanyDetailsDto;
import com.stock.market.company.entity.CompanyDetails;
import com.stock.market.company.entity.Price;
import com.stock.market.company.repository.CompanyDetailsRepository;
import com.stock.market.company.repository.MapperDynamo;
import com.stock.market.company.repository.SortStockRepository;
import com.stock.market.company.repository.StockPriceRepository;
import com.stock.market.company.service.ICompanyDetailsService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Roshini
 *
 */
@Service
@Log4j2
public class CompanyDetailsServiceImpl implements ICompanyDetailsService {

	@Autowired
	private CompanyDetailsRepository companyDetailsRepository;

	@Autowired
	StockPriceRepository stockPriceRepository;
	
	@Autowired
	private SortStockRepository sortStockRepository;
	
	@Autowired
	MapperDynamo map;

	@Transactional
	public List<CompanyDetailsDto> getAllCompanyDetailList() {
		List<CompanyDetailsDto> companyDetailsDtoList = new ArrayList<CompanyDetailsDto>();
		Iterable<CompanyDetails> companyDetailsList = companyDetailsRepository.findAll();
		Iterable<Price> priceList = sortStockRepository.findAll();
		log.info("CompanyDetailsController.getAllCompanyDetailList, companyDetailsList - {}", companyDetailsList);
		log.info("CompanyDetailsController.getAllCompanyDetailList, priceList - {}", priceList);
		if (companyDetailsList!=null) {

			for (CompanyDetails company : companyDetailsList) {
				double stckPrice = 0.0;
				for (Price price : priceList) {
					if (price.getCompanyCode().equalsIgnoreCase(company.getCompanyCode())) {
						stckPrice = price.getStckPrice();
//						break;
					}
				}
				CompanyDetailsDto companyDetailsDto = CompanyDetailsDto.builder().companyCode(company.getCompanyCode())
						.companyName(company.getCompanyName()).companyCEO(company.getCompanyCEO())
						.companyTurnOver(company.getCompanyTurnOver()).companyWebsite(company.getCompanyWebsite())
						.stockExchange(company.getStockExchange()).stockPrice(stckPrice).id(company.getCompanyCEO())
						.build();
				companyDetailsDtoList.add(companyDetailsDto);
			}
		}
		log.info("CompanyDetailsController.getAllCompanyDetailList, final response - {}", companyDetailsDtoList);
		return companyDetailsDtoList;
	}

	@Transactional
	public CompanyDetailsDto getCompanyDetailsByCompanyCode(String companyCode) {
		CompanyDetails companyDetails = companyDetailsRepository.findByCompanyCode(companyCode);
		Iterable<Price> priceList = sortStockRepository.findAll();
		List<Price> priceListByCompanyCode=filterByCompanyCode(companyCode,priceList);
		CompanyDetailsDto companyDetailsDto = null;
		log.info("CompanyDetailsController.getAllCompanyDetailList, companyDetails - {}", companyDetails);
		log.info("CompanyDetailsController.getAllCompanyDetailList, priceList - {}", priceList);
		if (companyDetails != null) {

			double stckPrice = 0.0;
			for (Price price : priceListByCompanyCode) {
				if (price.getCompanyCode().equalsIgnoreCase(companyDetails.getCompanyCode())) {
					stckPrice = price.getStckPrice();
//					break;
				}
			}
			companyDetailsDto = CompanyDetailsDto.builder().companyCode(companyDetails.getCompanyCode())
					.companyName(companyDetails.getCompanyName()).companyCEO(companyDetails.getCompanyCEO())
					.companyTurnOver(companyDetails.getCompanyTurnOver())
					.companyWebsite(companyDetails.getCompanyWebsite()).stockExchange(companyDetails.getStockExchange())
					.stockPrice(stckPrice).id(companyDetails.getCompanyCEO()).build();
		}
		log.info("CompanyDetailsController.getCompanyDetailsByCompanyCode(), final response - {}", companyDetailsDto);
		return companyDetailsDto;
	}

	private List<Price> filterByCompanyCode(String companyCode, Iterable<Price> priceList) {
		List<Price> priceListByCompanyCode=new ArrayList<Price>();
		for(Price pr:priceList) {
			if(pr.getCompanyCode().equals(companyCode)) {
				priceListByCompanyCode.add(pr);
			}
		}
		
		return priceListByCompanyCode;
		
	}

	@Override
	@Transactional
	public void registerCompanyDetail(CompanyDetails companyDetails) {
		log.info("CompanyDetailsController.registerCompanyDetail(),  {}", companyDetails);
		companyDetailsRepository.save(companyDetails);

	}

	@Override
	@Transactional
	public void deleteCompanyDetailsByCompanyCode(String companyCode) {
		log.info("CompanyDetailsController.registerCompanyDetail(),  {}", companyCode);
		map.deleteCompanyCodeCompanyDetails(companyCode);
	}
}

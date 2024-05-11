package com.fitapp.services.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	CustomerRepository customerRepository;

	public void createCustomerReqlvl1(CustomerRequestlevel1 customerRequest) {

		CustomerRegistration customer = new CustomerRegistration();
		customer.setCustomerid(customerRequest.getCustomerid());
		customer.setCustomerName(customerRequest.getCustomerName());
		customer.setMobileNo(customerRequest.getMobileNo());
		customer.setDateOfBirth(customerRequest.getDateOfBirth());
		customer.setGender(customerRequest.getGender());
		customer.custHeight.setHeight(customerRequest.custHeight.getHeight());
		customer.custHeight.setHeightUnits(customerRequest.custHeight.getHeightUnits());
		customer.custWeight.setWeight(customerRequest.custWeight.getWeight());
		customer.custWeight.setWeightUnits(customerRequest.custWeight.getWeightUnits());
		customerRepository.save(customer);
		log.info("Customer {} is saved: ", customer.getCustomerid());
	}
	
	public void updateCustomerReqlvl2(CustomerRequestlevel2 customerRequest) {

		Query query = new Query();
		
		query.addCriteria(Criteria.where("mobileNo").is(customerRequest.getMobileNo()));
		
		Update updateDefination = new Update();
		updateDefination.set("mobileNo", customerRequest.getMobileNo());
		updateDefination.set("fitnessLevel", customerRequest.getFitnessLevel());
		updateDefination.set("fitnessGoal", customerRequest.getFitnessGoal());
		updateDefination.set("bodyMassIndex", customerRequest.getBodyMassIndex());
		
		mongoTemplate.findAndModify(query, updateDefination, CustomerRegistration.class);
		
	}

	/*
	 * public List<ProductResponse> getAllProducts() { List<Product> products =
	 * productRepository.findAll();
	 * 
	 * return products.stream().map(this::mapToProductResponse).toList(); }
	 * 
	 * private ProductResponse mapToProductResponse(Product product) { return
	 * ProductResponse.builder() .id(product.getId()) .name(product.getName())
	 * .description(product.getDescription()) .price(product.getPrice()) .build(); }
	 */
}
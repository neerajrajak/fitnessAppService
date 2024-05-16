package com.fitapp.services.processor;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.models.PincodeMaster;
import com.fitapp.services.models.SocietyDbMaster;
import com.fitapp.services.models.SocietyDbNum;
import com.fitapp.services.models.CustomerNum;
import com.fitapp.services.repository.CustomerRepository;
import com.fitapp.services.repository.SocietyDbNumRepository;
import com.fitapp.services.repository.SocietyDbMasterRepository;
import com.fitapp.services.repository.PincodeMasterRepository;
import com.fitapp.services.repository.CustomerNumRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SocietyDbMasterRepository societyDbRepository;

	@Autowired
	PincodeMasterRepository pincodeMasterRepository;

	@Autowired
	CustomerNumRepository customerNumRepository;

	@Autowired
	SocietyDbNumRepository societyDbNumRepository;

	public CustomerRegistration createCustomerReqlvl1(CustomerRequestlevel1 customerRequest) {

		CustomerRegistration customer = new CustomerRegistration();
		customer.setCustomerid(StringUtils.leftPad(String.valueOf(getUsernNumNext()), 4, "0"));
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
		return customer;

	}

	public CustomerRegistration updateCustomerReqlvl2(CustomerRequestlevel2 customerRequest) {

		String housingSocietyId = customerRequest.getHousingSocietyId();

		if (null == housingSocietyId) {
			com.fitapp.services.models.SocietyDbMaster housingSocietyDtls = new SocietyDbMaster();
			housingSocietyId = StringUtils.leftPad(String.valueOf(getHousingSocNumNext()), 4, "0");
			housingSocietyDtls.setSocietyId(housingSocietyId);
			housingSocietyDtls.setSocietyName(customerRequest.housingSocietyDetails.getSocietyName());
			housingSocietyDtls.setAddressline1(customerRequest.housingSocietyDetails.getAddressline1());
			housingSocietyDtls.setAddressline2(customerRequest.housingSocietyDetails.getAddressline2());
			housingSocietyDtls.setPincode(customerRequest.housingSocietyDetails.getPincode());
			housingSocietyDtls.setCity(customerRequest.housingSocietyDetails.getCity());
			housingSocietyDtls.setState(customerRequest.housingSocietyDetails.getState());
			societyDbRepository.save(housingSocietyDtls);
		}

		Query query = new Query();

		query.addCriteria(Criteria.where("mobileNo").is(customerRequest.getMobileNo()));

		Update updateDefination = new Update();
		// updateDefination.set("mobileNo", customerRequest.getMobileNo());
		updateDefination.set("fitnessLevel", customerRequest.getFitnessLevel());
		updateDefination.set("fitnessGoal", customerRequest.getFitnessGoal());
		updateDefination.set("bodyMassIndex", customerRequest.getBodyMassIndex());
		updateDefination.set("totalDlyEnergyExpend", customerRequest.getTotalDlyEnergyExpend());
		updateDefination.set("housingSocietyId", housingSocietyId);

		CustomerRegistration customer = mongoTemplate.findAndModify(query, updateDefination,
				CustomerRegistration.class);
		return customer;

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

	public long getUsernNumNext() {
		CustomerNum last = customerNumRepository.findTopByOrderByIdDesc();

		if (Objects.isNull(last) || Objects.isNull(last.seq)) {
			last = new CustomerNum(0);
		}

		CustomerNum next = new CustomerNum(last.seq + 1);
		customerNumRepository.save(next);
		return next.seq;
	}

	public long getHousingSocNumNext() {
		SocietyDbNum last = societyDbNumRepository.findTopByOrderByIdDesc();

		if (Objects.isNull(last) || Objects.isNull(last.seq)) {
			last = new SocietyDbNum(0);
		}

		SocietyDbNum next = new SocietyDbNum(last.seq + 1);
		societyDbNumRepository.save(next);
		return next.seq;
	}

	public List<SocietyDbMaster> getSocietyDbMaster() {
		return societyDbRepository.findAll();

	}

	public List<PincodeMaster> getPincodeMaster() {
		return pincodeMasterRepository.findAll();

	}

}
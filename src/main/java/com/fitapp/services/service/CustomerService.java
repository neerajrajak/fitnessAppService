package com.fitapp.services.service;

import java.util.List;
import java.util.Objects;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fitapp.services.constants.FitAppConstants;
import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.exception.CustomerAlreadyExistException;
import com.fitapp.services.exception.NumberNotFoundException;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

	private final MongoTemplate mongoTemplate;

	private final CustomerRepository customerRepository;

	private final SocietyDbMasterRepository societyDbRepository;

	private final PincodeMasterRepository pincodeMasterRepository;

	private final CustomerNumRepository customerNumRepository;

	@Autowired
	SocietyDbNumRepository societyDbNumRepository;
	
	public  CustomerRegistration getCustomerDetails(String mobileNo) {
		CustomerRegistration customer = customerRepository.findByMobileNo(mobileNo);
		if(customer != null) {
			return customer;
		} else {
			throw new NumberNotFoundException(FitAppConstants.CUSTOMER_NOT_FOUND);
		}
	}

	public CustomerRegistration createCustomerReqlvl1(CustomerRequestlevel1 customerRequest) throws Exception {
		CustomerRegistration existingCust = customerRepository.findByMobileNo(customerRequest.getMobileNo());
		if(existingCust != null) {
			throw new CustomerAlreadyExistException(FitAppConstants.CUSTOMER_ALREADY_EXIST + existingCust.getCustomerName());
		} else {
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
	}

	@Transactional
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

        return mongoTemplate.findAndModify(query, updateDefination,
                CustomerRegistration.class);

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
		CustomerNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination,
					 CustomerNum.class);
		} else {
			last = new CustomerNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new CustomerNum(updatedSeq);
			customerNumRepository.save(next);
		}
		
		next = new CustomerNum(updatedSeq);
		return next.getSeq();
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
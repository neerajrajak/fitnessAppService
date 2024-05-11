package com.fitapp.services.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.CustomerRegistration;



public interface CustomerRepository extends MongoRepository<CustomerRegistration, String> {

	CustomerRegistration findByMobileNo(String mobileNo);

}
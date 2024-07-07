package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.OtpModel;

@Repository
public interface OtpRespository extends MongoRepository<OtpModel, String> {

	OtpModel findFirstByOrderByGeneratedOnDesc();

	OtpModel findFirstByMobileNoOrderByGeneratedOnDesc(String mobileNo);

	OtpModel findFirstByMobileNoAndOtpForOrderByGeneratedOnDesc(String mobileNo, String string);

}

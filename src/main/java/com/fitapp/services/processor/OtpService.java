package com.fitapp.services.processor;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fitapp.services.dto.OtpResponse;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.models.OtpModel;
import com.fitapp.services.repository.CustomerRepository;
import com.fitapp.services.repository.OtpRespository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

	private final OtpRespository OtpRepo;
	
	private final CustomerRepository custRepo;
	
	public OtpResponse sendOtp(String mobileNumber) {
		log.info("Inside send OTP service");
		CustomerRegistration user = custRepo.findByMobileNo(mobileNumber);
		
		if(user != null) {
			// generate otp
			int otp = getOtp();
			
			// save otp to db
			OtpModel otpModel = new OtpModel();
			otpModel.setOtp(otp);
			otpModel.setMobileNo(mobileNumber);
			otpModel.setGeneratedOn(LocalDateTime.now());
			
			OtpRepo.save(otpModel);
			
			// send sms
			
			// return response
			return OtpResponse.builder().isExistingUser(true).mobileNo(mobileNumber).otp(otp).generatedOn(otpModel.getGeneratedOn()).build();
		}
		return OtpResponse.builder().mobileNo(mobileNumber).isExistingUser(false).generatedOn(null).otp(0).build();
	}
	
	private int getOtp() {
	    // It will generate 4 digit random Number.
	    // from 0 to 9999
	    Random rnd = new Random();
	    int number = rnd.nextInt(9999);
	    System.out.println("OTp: "+ number);
	    // this will convert any number sequence into 6 character.
	    return number;
	}
}
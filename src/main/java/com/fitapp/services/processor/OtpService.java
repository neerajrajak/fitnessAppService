package com.fitapp.services.processor;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fitapp.services.constants.FitAppConstants;
import com.fitapp.services.dto.OtpResponse;
import com.fitapp.services.exception.NumberNotFoundException;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.models.OtpModel;
import com.fitapp.services.models.TrainerDetails;
import com.fitapp.services.repository.CustomerRepository;
import com.fitapp.services.repository.OtpRespository;
import com.fitapp.services.repository.TrainerDetailsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

	private final OtpRespository OtpRepo;

	private final CustomerRepository custRepo;
	
	private final TrainerDetailsRepository trainerRepo;

	public OtpResponse sendCustomerOtp(String mobileNumber) {
		log.info("Inside send OTP service");
		CustomerRegistration user = custRepo.findByMobileNo(mobileNumber);

		if (user != null) {
			// generate otp
			int otp = getOtp();
			String ecodedotp = encodeOtp(otp);

			// save otp to db
			OtpModel otpModel = new OtpModel();
			otpModel.setOtp(ecodedotp);
			otpModel.setMobileNo(mobileNumber);
			otpModel.setGeneratedOn(LocalDateTime.now());

			OtpRepo.save(otpModel);

			// send sms

			// return response
			return OtpResponse.builder().isExistingUser(true).mobileNo(mobileNumber).otp(otp)
					.generatedOn(otpModel.getGeneratedOn()).build();
		} else {
			throw new NumberNotFoundException(FitAppConstants.NUMBER_NOT_FOUND);
		}
		
	}

	public Boolean validateCustomerOtp(String mobileNo, int otp) {
		log.info("Inside validate Otp Service.");
		OtpModel otpRecord = OtpRepo.findFirstByMobileNoOrderByGeneratedOnDesc(mobileNo);
		if (otpRecord != null) {
			System.out.println("Validat Otp: " + otpRecord.getOtp());
			return (decodeOtp(otpRecord.getOtp()) == otp);
		}
		return false;
	}
	
	public OtpResponse sendTrainerOtp(String mobileNumber) {
		log.info("Inside send Trainer OTP service");
		TrainerDetails trainer = trainerRepo.findByMobileNo(mobileNumber);

		if (trainer != null) {
			// generate otp
			int otp = getOtp();
			String ecodedotp = encodeOtp(otp);

			// save otp to db
			OtpModel otpModel = new OtpModel();
			otpModel.setOtp(ecodedotp);
			otpModel.setMobileNo(mobileNumber);
			otpModel.setGeneratedOn(LocalDateTime.now());
			otpModel.setOtpFor("Trainer");
			OtpRepo.save(otpModel);

			// send sms

			// return response
			return OtpResponse.builder().isExistingUser(true).mobileNo(mobileNumber).otp(otp)
					.generatedOn(otpModel.getGeneratedOn()).build();
		} else {
			return OtpResponse.builder().isExistingUser(false).mobileNo(mobileNumber).otp(0)
					.generatedOn(null).build();
		}
		
	}
	
	public Boolean validateTrainerOtp(String mobileNo, int otp) {
		log.info("Inside validate Trainer Otp Service.");
		OtpModel otpRecord = OtpRepo.findFirstByMobileNoAndOtpForOrderByGeneratedOnDesc(mobileNo, "Trainer");
		if (otpRecord != null) {
			System.out.println("Validat Otp: " + otpRecord.getOtp());
			return (decodeOtp(otpRecord.getOtp()) == otp);
		}
		return false;
	}

	private int getOtp() {
		// It will generate 4 digit random Number.
		// from 0 to 9999
		Random rnd = new Random();
		int number = rnd.nextInt(9999);
		System.out.println("OTp: " + number);
		// this will convert any number sequence into 6 character.
		return number;
	}

	private String encodeOtp(int otp) {
		Base64.Encoder encoder = Base64.getMimeEncoder();
		String otpMessage = String.valueOf(otp);
		String encodedOtp = encoder.encodeToString(otpMessage.getBytes());
		return encodedOtp;
	}

	private int decodeOtp(String encodedOtp) {
		Base64.Decoder decoder = Base64.getMimeDecoder();
		// Decoding MIME encoded message
		String decodedOtp = new String(decoder.decode(encodedOtp));
		return Integer.parseInt(decodedOtp);
	}
}

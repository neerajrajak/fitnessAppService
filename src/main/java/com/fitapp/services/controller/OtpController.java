package com.fitapp.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.OtpResponse;
import com.fitapp.services.service.OtpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OtpController {

	@Autowired
	private OtpService otpService;

	@PostMapping("/send/{mobileNumber}")
	public OtpResponse sendOtp(@PathVariable String mobileNumber) {
		log.info("Inside send OTP controller");
		return otpService.sendCustomerOtp(mobileNumber);
	}

	@GetMapping("/validate/{mobileNo}/{otp}")
	public Boolean validateOtp(@PathVariable String mobileNo, @PathVariable int otp) {
		log.info("Inside validate Otp Controller");
		return otpService.validateCustomerOtp(mobileNo, otp);
	}
}

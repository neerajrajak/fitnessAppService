package com.fitapp.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.processor.OtpService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/trainer/otp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrainerOtpController {

	@Autowired
	private OtpService otpService;
}

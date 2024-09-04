package com.fitapp.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.NewTrainerRequest;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.models.TrainerDetails;
import com.fitapp.services.service.TrainerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/trainer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrainerController {

	@Autowired
	private TrainerService trainerService;
	
	@PostMapping("/registerTrainer")
	public ResponseEntity<TrainerDetails> createRequestLevel1(@RequestBody NewTrainerRequest newUser)
			throws Exception {
		TrainerDetails customer = trainerService.registerNewTrainer(newUser);
		return new ResponseEntity<TrainerDetails>(customer, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/details/{mobileNo}")
	public ResponseEntity<TrainerDetails> fetchCustomerDetails(@PathVariable String mobileNo){
		TrainerDetails trainer = trainerService.getTrainerDetails(mobileNo);
		return new ResponseEntity<TrainerDetails>(trainer, HttpStatus.OK);
	}
}

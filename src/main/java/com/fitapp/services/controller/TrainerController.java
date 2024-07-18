package com.fitapp.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.NewTrainerRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.models.SessionDetails;
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
	
	@PostMapping("/createSession")
	public ResponseEntity<SessionDetails> createSession(@RequestBody SessionRequest sessionRequest)
			throws Exception {
		SessionDetails customer = trainerService.createNewSession(sessionRequest);
		return new ResponseEntity<SessionDetails>(customer, HttpStatus.CREATED);
	}
}

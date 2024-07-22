package com.fitapp.services.controller;

import java.util.List;

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

import com.fitapp.services.dto.SessionDetailRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.models.SessionDetails;
import com.fitapp.services.service.SessionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/session")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SessionScheduleController {

	@Autowired
	private SessionService sessionService;

	@PostMapping("/createSession")
	public ResponseEntity<SessionDetails> createSession(@RequestBody SessionRequest sessionRequest) throws Exception {
		SessionDetails customer = sessionService.createNewSession(sessionRequest);
		return new ResponseEntity<SessionDetails>(customer, HttpStatus.CREATED);
	}

	@GetMapping("/getSession/{sessionId}")
	public ResponseEntity<SessionDetails> getSessionDetails(@PathVariable  String sessionId) throws Exception {
		SessionDetails customer = sessionService.getSessionDetails(sessionId);
		return new ResponseEntity<SessionDetails>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/getTrainerSessionDetail")
	public ResponseEntity<List<SessionDetails>> getTrainerSessionDetail(@RequestBody  SessionDetailRequest request) throws Exception {
		List<SessionDetails> customer = sessionService.getTrainerSessionDetail(request);
		return new ResponseEntity<List<SessionDetails>>(customer, HttpStatus.OK);
	}
}

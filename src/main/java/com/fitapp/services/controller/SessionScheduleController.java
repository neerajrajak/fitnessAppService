package com.fitapp.services.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.ClientRecordDto;
import com.fitapp.services.dto.EquipmentChecklistDto;
import com.fitapp.services.dto.MarkAttendance;
import com.fitapp.services.dto.RatingDto;
import com.fitapp.services.dto.SessionDetailRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.dto.TainerNotesDto;
import com.fitapp.services.exception.SessionException;
import com.fitapp.services.models.ClientRecord;
import com.fitapp.services.models.ClientSessionDetails;
import com.fitapp.services.models.Equipment;
import com.fitapp.services.models.Equipment.AvailableWith;
import com.fitapp.services.models.EquipmentChecklist;
import com.fitapp.services.models.Rating;
import com.fitapp.services.models.SessionDetails;
import com.fitapp.services.models.TrainerDashboardDetail;
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
	public ResponseEntity<TrainerDashboardDetail> getTrainerSessionDetail(@RequestBody  SessionDetailRequest request) throws Exception {
		TrainerDashboardDetail trainerDashboardDetail = sessionService.getTrainerSessionDetail(request);
		return new ResponseEntity<TrainerDashboardDetail>(trainerDashboardDetail, HttpStatus.OK);
	}
	
	@GetMapping("/getClientDetail/{clientId}")
	public ResponseEntity<ClientRecord> getClientDetail(@PathVariable  String clientId) throws Exception {
		ClientRecord clientRecord = sessionService.getClientDetail(clientId);
		return new ResponseEntity<ClientRecord>(clientRecord, HttpStatus.OK);
	}
	
	@PostMapping("/addClientDetails")
	public ResponseEntity<ClientRecord> addClientDetails(@RequestBody ClientRecordDto clientRecordDto) throws Exception {
		ClientRecord clientRecord = sessionService.addAndUpdateClientDetails(clientRecordDto);
		return new ResponseEntity<ClientRecord>(clientRecord, HttpStatus.OK);
	}
	
	@PostMapping("/addTrainerNotes")
	public ResponseEntity<ClientRecord> addTrainerNotes(@RequestBody TainerNotesDto tainerNotesDto) throws Exception {
		ClientRecord clientRecord = sessionService.addTrainerNotes(tainerNotesDto);
		return new ResponseEntity<ClientRecord>(clientRecord, HttpStatus.OK);
	}
	
	@PostMapping("/markAttendance")
	public ResponseEntity<Boolean> markAttendance(@RequestBody MarkAttendance attendance) throws Exception {
		boolean success = sessionService.MarkAttendance(attendance);
		return new ResponseEntity<Boolean>(success, HttpStatus.OK);
	}
	
	@GetMapping("/startEndSession/{sessionId}/{state}")
	public ResponseEntity<SessionDetails> startAndEndSession(@PathVariable String sessionId,@PathVariable String state){
		SessionDetails sessionDetails = sessionService.startAndEndSession(sessionId,state);
		return new ResponseEntity<SessionDetails>(sessionDetails, HttpStatus.OK);
	}
	
	@GetMapping("/markRunningLate/{sessionId}")
	public ResponseEntity<SessionDetails> markRunningLate(@PathVariable String sessionId,@RequestParam String timing){
		SessionDetails sessionDetails = sessionService.markRunningLate(sessionId,timing);
		return new ResponseEntity<SessionDetails>(sessionDetails, HttpStatus.OK);
	}
	
	@PostMapping("/updateCheckList")
	public ResponseEntity<EquipmentChecklist> updateCheckList(@RequestBody EquipmentChecklistDto equipmentChecklistDto){
		EquipmentChecklist checkList = sessionService.updateCheckList(equipmentChecklistDto);
		return new ResponseEntity<EquipmentChecklist>(checkList, HttpStatus.OK);
	}
	
	@PostMapping("/addEquipmentMaster")
	public ResponseEntity<Equipment> addEquipment(@RequestBody Equipment equipment){
		Equipment equipmentResult = sessionService.addEquipment(equipment);
		return new ResponseEntity<Equipment>(equipmentResult, HttpStatus.OK);
	}
	
	@GetMapping("/getEquipmentMaster")
	public ResponseEntity<Map<AvailableWith,List<Equipment>>> getEquipment(){
		Map<AvailableWith,List<Equipment>> equipment = sessionService.getEquipment();
		return new ResponseEntity<Map<AvailableWith,List<Equipment>>>(equipment, HttpStatus.OK);
	}
	
	@PostMapping("/submitRating")
	public ResponseEntity<List<Rating>> submitRating(@RequestBody List<RatingDto> rating) throws SessionException{
		List<Rating> result = sessionService.submitRating(rating);
		return new ResponseEntity<List<Rating>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/client/sessionDetails/{sessionId}")
	public ResponseEntity<SessionDetails> getClientSessionDetails(@PathVariable  String sessionId) throws Exception {
		SessionDetails sessionDetails = sessionService.getClientSessionDetails(sessionId);
		return new ResponseEntity<SessionDetails>(sessionDetails, HttpStatus.OK);
	}
	
	@PostMapping("/getClientSessionDetail")
	public ResponseEntity<List<ClientSessionDetails>> getClientSessionDetail(@RequestBody  SessionDetailRequest request) throws Exception {
		List<ClientSessionDetails> clientSessionDetail = sessionService.getClientSessionDetail(request);
		return new ResponseEntity<List<ClientSessionDetails>>(clientSessionDetail, HttpStatus.OK);
	}
	
	@PostMapping("/addClientSessionDetail")
	public ResponseEntity<ClientSessionDetails> addClientSessionDetail(@RequestBody  SessionRequest request) throws Exception {
		ClientSessionDetails sessionDetail = sessionService.addClientSessionDetail(request);
		return new ResponseEntity<ClientSessionDetails>(sessionDetail, HttpStatus.OK);
	}
}

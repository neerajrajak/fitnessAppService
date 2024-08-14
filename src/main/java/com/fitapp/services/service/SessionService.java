package com.fitapp.services.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitapp.services.dto.ClientRecordDto;
import com.fitapp.services.dto.SessionDetailRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.models.ClientRecord;
import com.fitapp.services.models.CustomerNum;
import com.fitapp.services.models.SessionDestailNum;
import com.fitapp.services.models.SessionDetails;
import com.fitapp.services.models.TrainerNotes;
import com.fitapp.services.repository.ClientRecordNumRepository;
import com.fitapp.services.repository.ClientRecordRepository;
import com.fitapp.services.repository.SessionDestailNumRepository;
import com.fitapp.services.repository.SessionDetailsRepositpry;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

	private final MongoTemplate mongoTemplate;

	private final SessionDetailsRepositpry sessionDetailsRepositpry;

	private final SessionDestailNumRepository sessionDestailNumRepository;

	private ObjectMapper objectMapper;

	private final ClientRecordNumRepository clientRecordNumRepository;
	
	private final ClientRecordRepository clientRecordRepository;

	@PostConstruct
	public void configObjectMapper() {
		objectMapper = new ObjectMapper();
		JavaTimeModule module = new JavaTimeModule();
		objectMapper.registerModule(module);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public SessionDetails createNewSession(SessionRequest sessionRequest) {
		SessionDetails sessionDetails = objectMapper.convertValue(sessionRequest, SessionDetails.class);
		sessionDetails.setSessionId(StringUtils.leftPad(String.valueOf(getNextSessionId()), 4, "0"));
		sessionDetails = sessionDetailsRepositpry.save(sessionDetails);
		log.info("session {} is saved: ", sessionDetails.getSessionId());
		return sessionDetails;
	}

	public long getNextSessionId() {
		SessionDestailNum last = sessionDestailNumRepository.findTopByOrderByIdDesc();
		SessionDestailNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, SessionDestailNum.class);
		} else {
			last = new SessionDestailNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new SessionDestailNum(updatedSeq);
			sessionDestailNumRepository.save(next);
		}

		next = new SessionDestailNum(updatedSeq);
		return next.getSeq();
	}

	public SessionDetails getSessionDetails(String sessionId) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(sessionId);
		return sessionDetails;
	}

	public List<SessionDetails> getTrainerSessionDetail(SessionDetailRequest request) {
		Collections.sort(request.getDate());
		LocalDateTime startDate = LocalDateTime.of(request.getDate().get(0).toLocalDate(), LocalTime.MIDNIGHT);;
		LocalDateTime endDate = LocalDateTime.of(request.getDate().get(request.getDate().size()-1).toLocalDate(),LocalTime.MAX);
		List<SessionDetails> sessionDetails = sessionDetailsRepositpry.findAllByTrainerIdAndStartTimeBetweenOrderByStartTimeDesc(
				request.getTrainerId(),startDate,endDate);	
		return sessionDetails;
	}
	
	public ClientRecord getClientDetail(String clientId,String sessionId) {
		Optional<ClientRecord> clientRecord= clientRecordRepository.findBySessionIdAndClientRecordId(sessionId,clientId);
		if(clientRecord.isPresent()) {
			return clientRecord.get();
		}
		return null;
	}
	
	public ClientRecord addClientDetails( ClientRecordDto clientRecordDto) {
		ClientRecord clientRecord = objectMapper.convertValue(clientRecordDto, ClientRecord.class);
			
	    clientRecord.setClientRecordId(StringUtils.leftPad(String.valueOf(getNextClientRecordId()), 4, "0"));
		
		clientRecord = clientRecordRepository.save(clientRecord);
		log.info("session {} is saved: ", clientRecord.getSessionId());
		return clientRecord;
	}
	
	public long getNextClientRecordId() {
		CustomerNum last = clientRecordNumRepository.findTopByOrderByIdDesc();
		CustomerNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, CustomerNum.class);
		} else {
			last = new CustomerNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new CustomerNum(updatedSeq);
			clientRecordNumRepository.save(next);
		}
		next = new CustomerNum(updatedSeq);
		return next.getSeq();
	}
}

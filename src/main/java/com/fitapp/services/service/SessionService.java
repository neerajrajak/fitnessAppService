package com.fitapp.services.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitapp.services.dto.ClassInfo;
import com.fitapp.services.dto.ClientInfo;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.dto.WorkoutPlans;
import com.fitapp.services.models.SessionDestailNum;
import com.fitapp.services.models.SessionDetails;
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
		System.out.println();
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
}

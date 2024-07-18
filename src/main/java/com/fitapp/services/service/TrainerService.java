package com.fitapp.services.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitapp.services.dto.NewTrainerRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.models.SessionDestailNum;
import com.fitapp.services.models.SessionDetails;
import com.fitapp.services.models.TrainerDbNum;
import com.fitapp.services.models.TrainerDetails;
import com.fitapp.services.repository.SessionDestailNumRepository;
import com.fitapp.services.repository.SessionDetailsRepositpry;
import com.fitapp.services.repository.TrainerDbNumRepository;
import com.fitapp.services.repository.TrainerDetailsRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerService {

	private final MongoTemplate mongoTemplate;

	private final TrainerDetailsRepository trainerDetailsRepository;
	
	private final SessionDetailsRepositpry sessionDetailsRepositpry;

	private final TrainerDbNumRepository trainerDbNumRepository;
	
	private final SessionDestailNumRepository sessionDestailNumRepository;

	private ObjectMapper objectMapper;

	@PostConstruct
	public void configObjectMapper() {
		objectMapper = new ObjectMapper();
		JavaTimeModule module = new JavaTimeModule();
		objectMapper.registerModule(module);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public TrainerDetails registerNewTrainer(NewTrainerRequest trainerRequest) {
		TrainerDetails existingTrainer = trainerDetailsRepository.findByMobileNo(trainerRequest.getMobileNo());
		if (null != existingTrainer) {
			return null;
		} else {
			TrainerDetails trainerDetails = objectMapper.convertValue(trainerRequest,TrainerDetails.class);
			trainerDetails.setTrainerId(StringUtils.leftPad(String.valueOf(getNextTrainerId()), 4, "0"));
			trainerDetailsRepository.save(trainerDetails);		
			log.info("Trainer {} is saved: ", trainerDetails.getTrainerId());
			return trainerDetails;
		}
	}
	
	public SessionDetails createNewSession(SessionRequest sessionRequest) {
		SessionDetails sessionDetails = objectMapper.convertValue(sessionRequest,SessionDetails.class);
		sessionDetails.setSessionId(StringUtils.leftPad(String.valueOf(getNextSessionId()), 4, "0"));

		sessionDetails = sessionDetailsRepositpry.save(sessionDetails);		
		log.info("session {} is saved: ", sessionDetails.getSessionId());
		return sessionDetails;
	}

	public long getNextTrainerId() {
		TrainerDbNum last = trainerDbNumRepository.findTopByOrderByIdDesc();
		TrainerDbNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, TrainerDbNum.class);
		} else {
			last = new TrainerDbNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new TrainerDbNum(updatedSeq);
			trainerDbNumRepository.save(next);
		}

		next = new TrainerDbNum(updatedSeq);
		return next.getSeq();
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

}

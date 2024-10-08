package com.fitapp.services.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.ClientSessionDetails;

public interface ClientSessionDetailsRepository extends MongoRepository<ClientSessionDetails, String>{

	ClientSessionDetails findBySessionId(String sessionId);

	List<ClientSessionDetails> findAllByTrainerIdAndStatusAndStartTimeBetweenOrderByStartTimeDesc(String clientId,
			String status, LocalDateTime endDate);

	ClientSessionDetails findByClientIdAndSessionId(String clientId, String sessionId);

	List<ClientSessionDetails> findAllByClientIdAndStatusAndStartTimeBetweenOrderByStartTimeDesc(String clientId,
			String string, LocalDateTime startDate, LocalDateTime endDate);

}

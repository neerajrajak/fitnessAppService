package com.fitapp.services.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.SessionDetails;

@Repository
public interface SessionDetailsRepositpry extends MongoRepository<SessionDetails, String>{

	SessionDetails findByTrainerId(String trainerId);

	SessionDetails findBySessionId(String sessionId);

	List<SessionDetails> findAllByTrainerIdAndStartTimeBetweenOrderByStartTimeDesc(String trainerId, LocalDateTime startDate,
			LocalDateTime endDate);

	List<SessionDetails> findAllByTrainerId(String trainerId);
	
}
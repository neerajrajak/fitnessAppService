package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.SessionDetails;

@Repository
public interface SessionDetailsRepositpry extends MongoRepository<SessionDetails, String>{

	SessionDetails findByTrainerId(String trainerId);
	
}
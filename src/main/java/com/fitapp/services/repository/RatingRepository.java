package com.fitapp.services.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.Rating;

public interface RatingRepository extends MongoRepository<Rating, String>{

	List<Rating> findAllBySessionId(String sessionId);

	Rating findByEntityIdAndEntityTypeAndSessionId(String entityId, String entityType, String sessionId);

}

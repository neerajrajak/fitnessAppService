package com.fitapp.services.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.SessionDestailNum;

public interface SessionDestailNumRepository extends MongoRepository<SessionDestailNum, ObjectId>{
	
	SessionDestailNum findTopByOrderByIdDesc();

}

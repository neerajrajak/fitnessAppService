package com.fitapp.services.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.SocietyDbNum;

@Repository
public interface SocietyDbNumRepository extends MongoRepository<SocietyDbNum, ObjectId> {
	SocietyDbNum findTopByOrderByIdDesc();
}

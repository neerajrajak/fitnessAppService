package com.fitapp.services.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.CustomerNum;

@Repository
public interface CustomerNumRepository extends MongoRepository<CustomerNum, ObjectId> {
	CustomerNum findTopByOrderByIdDesc();
}

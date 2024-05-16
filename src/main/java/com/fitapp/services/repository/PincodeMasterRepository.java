package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.PincodeMaster;

@Repository
public interface PincodeMasterRepository extends MongoRepository<PincodeMaster, String> {
}

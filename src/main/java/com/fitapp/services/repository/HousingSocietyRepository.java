package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.HousingSocietyDetails;

@Repository
public interface HousingSocietyRepository extends MongoRepository<HousingSocietyDetails, String> {}

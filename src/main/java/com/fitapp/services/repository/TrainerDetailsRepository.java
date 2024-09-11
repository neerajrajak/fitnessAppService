package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.TrainerDetails;

@Repository
public interface TrainerDetailsRepository extends MongoRepository<TrainerDetails, String> {

	TrainerDetails findByMobileNo(String mobileNo);

	TrainerDetails findByTrainerId(String trainerId);

}

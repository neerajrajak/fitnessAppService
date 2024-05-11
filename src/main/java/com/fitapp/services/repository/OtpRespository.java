package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.OtpModel;



@Repository
public interface OtpRespository extends MongoRepository<OtpModel, String> {

}

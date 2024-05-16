package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.SocietyDbMaster;

@Repository
public interface SocietyDbMasterRepository extends MongoRepository<SocietyDbMaster, String> {}

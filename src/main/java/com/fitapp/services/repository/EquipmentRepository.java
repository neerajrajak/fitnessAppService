package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.Equipment;

public interface EquipmentRepository  extends MongoRepository<Equipment, String>{

}

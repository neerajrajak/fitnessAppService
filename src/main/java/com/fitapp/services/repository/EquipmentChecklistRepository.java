package com.fitapp.services.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitapp.services.models.EquipmentChecklist;

public interface EquipmentChecklistRepository  extends MongoRepository<EquipmentChecklist, String>{

	EquipmentChecklist findBySessionId(String sessionId);

}

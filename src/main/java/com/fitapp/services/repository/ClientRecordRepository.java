package com.fitapp.services.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.ClientRecord;

@Repository
public interface ClientRecordRepository extends MongoRepository<ClientRecord, String>{

	Optional<ClientRecord> findBySessionIdAndClientRecordId(String sessionId, String clientId);

	Optional<ClientRecord> findBySessionIdAndClientId(String sessionId, String clientId);

}

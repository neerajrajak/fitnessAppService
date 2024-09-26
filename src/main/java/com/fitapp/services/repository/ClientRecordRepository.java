package com.fitapp.services.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.ClientRecord;

@Repository
public interface ClientRecordRepository extends MongoRepository<ClientRecord, String>{

	Optional<ClientRecord> findBySessionIdAndClientId(String sessionId, String clientId);

	List<ClientRecord> findBySessionIdAndClientIdIn(String sessionId, List<String> clientIds);

	ClientRecord findByClientId(String clientId);

	List<ClientRecord> findAllByClientIdIn(List<String> clientIds);

}

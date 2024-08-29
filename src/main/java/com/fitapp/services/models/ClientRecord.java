package com.fitapp.services.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fitapp.services.dto.AttendedSession;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "client_record")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRecord {
	@Id
	private String id;
	
	private String clientRecordId;
	private String trainerId;
	private String sessionId;
	private String trainerName;
	private String clientId;
	private LocalDate memberSince;
	private List<AttendedSession> sessionAttended;
	private String clientName;
	private String clientAge;
	private String fitnessLevel;
	private String attendance;
	private String totalSession;
	private List<String> fitnessGoal;
	private String activityStreak;
	private String emergencyContactNo;
	private String emergencyContactName;
	private Map<String,List<String>> issues;
	private List<TrainerNotes> trainerNotes;
	
}

package com.fitapp.services.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fitapp.services.models.TrainerNotes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRecordDto {
	private String id;
	private String clientRecordId;
	private String trainerId;
	private String sessionId;
	private String trainerName;
	private LocalDate memberSince;
	private List<AttendedSession> sessionAttended;
	private String clientName;
	private String clientAge;
	private String clientId;
	private String fitnessLevel;
	private String attendance;
	private String totalSession;
	private List<String> fitnessGoal;
	private String activityStreak;
	private String emergencyContactNo;
	private String emergencyContactName;
	private Map<String, List<String>> issues;
	private List<TrainerNotes> trainerNotes;

}

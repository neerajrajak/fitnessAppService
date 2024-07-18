package com.fitapp.services.models;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "session_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionDetails {
	
	@Id
	private String id;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String sessionId;
	
	private Instant startTime;
	private Instant endTime;
	private String location;
	private double totalhours;
	private String trainingName;
	private String Attendance;
	private String toBeTrainedBy;
	private String trainerId;
	private String trainerBuddy;
	private String trainerBuddyId;

}

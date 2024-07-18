package com.fitapp.services.dto;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionRequest {

	@Id
	private String id;

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

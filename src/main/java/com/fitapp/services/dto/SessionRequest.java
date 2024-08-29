package com.fitapp.services.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	
	private String sessionId;
	private LocalDateTime startTime;
	private LocalDateTime actualStartTime;
	private LocalDateTime endTime;
	private LocalDateTime actualEndTime;
	private String location;
	private double totalhours;
	private double actualTotalhours;
	private String trainingName;
	private List<ClientAttendanceInfo> clientAttendance; 
	private String toBeTrainedBy;
	private String trainerId;
	private String trainerBuddy;
	private String trainerBuddyId;
	private String status;
	private ClassInfo classInfo;
	private List<WorkoutPlans> workoutPlans;
	private List<ClientInfo> clientInfo;
}

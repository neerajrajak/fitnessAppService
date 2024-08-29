package com.fitapp.services.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fitapp.services.dto.ClassInfo;
import com.fitapp.services.dto.ClientAttendanceInfo;
import com.fitapp.services.dto.ClientInfo;
import com.fitapp.services.dto.WorkoutPlans;

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

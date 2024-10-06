package com.fitapp.services.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fitapp.services.dto.ClassInfo;
import com.fitapp.services.dto.ClientAttendanceInfo;
import com.fitapp.services.dto.ClientInfo;
import com.fitapp.services.dto.WorkoutPlans;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(value = "client_session_details")
@AllArgsConstructor
@NoArgsConstructor
public class ClientSessionDetails {
	
	@Id
	private String id;
	
	private String sessionId;
	private LocalDateTime startTime;
	private LocalDateTime actualStartTime;
	private LocalDateTime endTime;
	private LocalDateTime actualEndTime;
	private String location;
	private String runningLateTime;
	private String sessionSummary;
	private String sessionLevel;
	private String calorie;
	private String trainingName;
	private String toBeTrainedBy;
	private String trainerId;
	private String trainerBuddy;
	private String sessionRemark;
	private String trainerBuddyId;
	private String status;
	private double rescheduleCount;

}

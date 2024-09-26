package com.fitapp.services.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Transient;
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
	private String runningLateTime;
	private double totalhours;
	private double actualTotalhours;
	private String trainingName;
	private List<ClientAttendanceInfo> clientAttendance; 
	private String toBeTrainedBy;
	private String trainerId;
	private String trainerBuddy;
	private String sessionRemark;
	private String trainerBuddyId;
	private String status;
	private ClassInfo classInfo;
	private List<WorkoutPlans> workoutPlans;
	private List<ClientInfo> clientInfo;
	
	@Transient
	private Map<String,Double> clientCount;
	
	@Transient
	private String previousWorkout;
	
	@Transient
	private String previousWorkoutTime;

}

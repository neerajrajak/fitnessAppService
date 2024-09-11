package com.fitapp.services.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerNotes {
	
	private String trainerId;
	private String trainerName;
	private LocalDateTime date;
	private String workOutName;
	private String trainerSpeciality;
	private String remark;

}

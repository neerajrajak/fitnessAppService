package com.fitapp.services.dto;

import com.fitapp.services.models.TrainerNotes;

import lombok.Data;

@Data
public class TainerNotesDto {

	private TrainerNotes trainerNotes;	
	private String sessionId;
	private String sessionName;
	private String clientId;
	
}

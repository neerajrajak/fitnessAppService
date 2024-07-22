package com.fitapp.services.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SessionDetailRequest {
	
	private String trainerId;
	private List<LocalDateTime> date;

}

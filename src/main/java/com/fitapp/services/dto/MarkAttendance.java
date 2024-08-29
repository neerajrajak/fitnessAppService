package com.fitapp.services.dto;

import java.util.List;

import lombok.Data;

@Data
public class MarkAttendance {
	private String sessionId;
	private List<ClientAttendanceInfo> clientAttendance; 
}

package com.fitapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInfo {
	
	private String clientId;
	private String clientName;
	private String age;
	private String gender;
	private String medicalIssue;

}

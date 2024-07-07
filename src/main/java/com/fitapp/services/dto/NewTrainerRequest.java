package com.fitapp.services.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTrainerRequest {
	private String mobileNo;
	private String gender;
	private String speciality;
	private LocalDate trainerSince;
}

package com.fitapp.services.models;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "trainer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerDetails {
	
	@Id
	private String id;
	private String trainerId;
	private String mobileNo;
	private String gender;
	private String speciality;
	private LocalDate trainerSince;

}

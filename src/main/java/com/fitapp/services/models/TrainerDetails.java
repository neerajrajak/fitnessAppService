package com.fitapp.services.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
	@NotNull(message="name can't be null")
	@NotBlank
	private String trainerName;
	@NotNull(message="mobile no can't be null")
	@NotBlank
	private String mobileNo;
	private String gender;
	private double age;
	@NotNull(message="speciality can't be null")
	@NotBlank
	private String speciality;
	private LocalDate trainerSince;

}

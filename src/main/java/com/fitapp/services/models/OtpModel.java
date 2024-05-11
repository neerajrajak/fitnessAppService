package com.fitapp.services.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "otp")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OtpModel {
	@Id
	private String id;
	private String mobileNo;
	private int otp;
	private LocalDateTime generatedOn;
}

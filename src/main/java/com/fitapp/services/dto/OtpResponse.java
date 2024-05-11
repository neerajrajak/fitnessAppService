package com.fitapp.services.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpResponse {

	private int otp;
	private boolean isExistingUser;
	private String mobileNo;
	private LocalDateTime generatedOn;
}

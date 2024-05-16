package com.fitapp.services.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "pincodemaster")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PincodeMaster {

	@Id
	private String pincode;
	private String district;
	private String state;
}

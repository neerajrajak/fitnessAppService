package com.fitapp.services.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "societydbmaster")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocietyDbMaster {

	@Id
	private String societyId;
	private String societyName;
	private String addressline1;
	private String addressline2;
	private String pincode;
	private String city;
	private String state;

}

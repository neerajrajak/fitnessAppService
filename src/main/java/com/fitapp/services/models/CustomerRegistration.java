package com.fitapp.services.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRegistration {

	@Id
	private String customerid;
	public String customerName;
	private String mobileNo;
	private String dateOfBirth;
	private String gender;
	private String fitnessLevel;
	private List<String> fitnessGoal = new ArrayList<String>();
	private double bodyMassIndex;
	public CustomerHeight custHeight = new CustomerHeight();
	public CustomerWeight custWeight = new CustomerWeight();
	public TargetHeight tarHeight = new TargetHeight();
	public TargetWeight tarWeight = new TargetWeight();
	private String housingSocietyId;

	@Component
	@Data
	public class CustomerHeight {

		private double height;
		private String heightUnits;
	}

	@Component
	@Data
	public class CustomerWeight {
		private double weight;
		private String weightUnits;

	}

	@Component
	@Data
	public class TargetHeight {

		private double height;
		private String heightUnits;
	}

	@Component
	@Data
	public class TargetWeight {
		private double weight;
		private String weightUnits;

	}

}

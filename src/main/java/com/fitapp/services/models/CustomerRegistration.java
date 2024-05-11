package com.fitapp.services.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	public HousingSocietyDetails housingSocietyDetails = new HousingSocietyDetails();

	@Component
	@Getter
	@Setter
	public class CustomerHeight {

		private double height;
		private String heightUnits;
	}

	@Component
	@Getter
	@Setter
	public class CustomerWeight {
		private double weight;
		private String weightUnits;

	}

	@Component
	@Getter
	@Setter
	public class TargetHeight {

		private double height;
		private String heightUnits;
	}

	@Component
	@Getter
	@Setter
	public class TargetWeight {
		private double weight;
		private String weightUnits;

	}

	@Component
	@Getter
	@Setter
	public class HousingSocietyDetails {
		String societyName;
		String addressline1;
		String addressline2;
		String pincode;
		String city;
		String state;
		double weight;
		String weightUnits;

	}

}

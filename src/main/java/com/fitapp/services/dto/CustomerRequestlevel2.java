package com.fitapp.services.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestlevel2 {
	//private String customerid;
	private String mobileNo;
	private String fitnessLevel;
	private List<String> fitnessGoal = new ArrayList<String>();
	private double bodyMassIndex;
	//public HousingSocietyDetails housingSocietyDetails = new HousingSocietyDetails();

	/*
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
	*/
}
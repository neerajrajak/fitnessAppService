package com.fitapp.services.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestlevel2 {
	private String customerid;
	private String mobileNo;
	private String fitnessLevel;
	private List<String> fitnessGoal = new ArrayList<String>();
	private double bodyMassIndex;
	private double totalDlyEnergyExpend;
	private String housingSocietyId;
	public HousingSocietyDetailsReq housingSocietyDetails = new HousingSocietyDetailsReq();

	@Component
	@Data
	public class HousingSocietyDetailsReq {
		String societyName;
		String addressline1;
		String addressline2;
		String pincode;
		String city;
		String state;

	}

}
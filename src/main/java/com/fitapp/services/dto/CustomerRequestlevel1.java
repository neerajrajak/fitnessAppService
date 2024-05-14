package com.fitapp.services.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestlevel1 {
	private String customerid;
	private String customerName;
	private String mobileNo;
	private String dateOfBirth;
	private String gender;
	public CustomerHeight custHeight = new CustomerHeight();
	public CustomerWeight custWeight = new CustomerWeight();

	@Component
	@Data
	public class CustomerHeight {

		double height;
		String heightUnits;
	}

	@Component
	@Data
	public class CustomerWeight {
		double weight;
		String weightUnits;

	}
}
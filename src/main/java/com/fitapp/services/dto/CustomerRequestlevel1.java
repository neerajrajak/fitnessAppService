package com.fitapp.services.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Getter
	@Setter
	public class CustomerHeight {

		double height;
		String heightUnits;
	}

	@Component
	@Getter
	@Setter
	public class CustomerWeight {
		double weight;
		String weightUnits;

	}
}
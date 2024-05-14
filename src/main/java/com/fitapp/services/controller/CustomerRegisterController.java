
package com.fitapp.services.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.processor.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class CustomerRegisterController {

	CustomerService customerService = new CustomerService();

	@PostMapping("/createUser")
	public ResponseEntity<CustomerRegistration> createRequestLevel1(@RequestBody CustomerRequestlevel1 newUser) {

		try {

			CustomerRegistration customer =  customerService.createCustomerReqlvl1(newUser);
			return new ResponseEntity<CustomerRegistration>(customer, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/addGoals")
	public ResponseEntity<CustomerRegistration> createRequestLevel2(@RequestBody CustomerRequestlevel2 newUser) {

		try {

			CustomerRegistration customer = customerService.updateCustomerReqlvl2(newUser);
			return new ResponseEntity<CustomerRegistration>(customer, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}

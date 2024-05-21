
package com.fitapp.services.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.models.CustomerRegistration;
import com.fitapp.services.models.PincodeMaster;
import com.fitapp.services.models.SocietyDbMaster;
import com.fitapp.services.processor.CustomerService;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerRegisterController {

	CustomerService customerService = new CustomerService();

	@PostMapping("/createUser")
	public ResponseEntity<CustomerRegistration> createRequestLevel1(@RequestBody CustomerRequestlevel1 newUser) {

		try {

			CustomerRegistration customer = customerService.createCustomerReqlvl1(newUser);
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

	@GetMapping("/societydbmaster")
	public ResponseEntity<?> getSocietyDbMaster() {

		try {
			List<SocietyDbMaster> societyDbDetails = customerService.getSocietyDbMaster();
			return new ResponseEntity<>(societyDbDetails, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}

	}

	@GetMapping("/pincodemaster")
	public ResponseEntity<?> getPincodeMaster() {

		try {
			List<PincodeMaster> pincodemaster = customerService.getPincodeMaster();
			return new ResponseEntity<>(pincodemaster, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}

	}

}

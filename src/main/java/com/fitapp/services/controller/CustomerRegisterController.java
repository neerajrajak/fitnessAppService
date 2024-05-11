
package com.fitapp.services.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fitapp.services.dto.CustomerRequestlevel1;
import com.fitapp.services.dto.CustomerRequestlevel2;
import com.fitapp.services.processor.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class CustomerRegisterController {

	// @Autowired
	// CustomerDataProcessor custDataProcessor;

	// @Autowired
	// RegistrationDao sc;

	CustomerService customerService = new CustomerService();

	@PostMapping("/create1")
	public ResponseEntity<String> createRequestLevel1(@RequestBody CustomerRequestlevel1 newUser, HttpServletRequest request) {

		try {
			// sc.saveEmployeeByPreparedStatement(newUser);
			customerService.createCustomerReqlvl1(newUser);
			return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}
	}
	
	@PostMapping("/create2")
	public ResponseEntity<String> createRequestLevel2(@RequestBody CustomerRequestlevel2 newUser, HttpServletRequest request) {

		try {
			// sc.saveEmployeeByPreparedStatement(newUser);
			customerService.updateCustomerReqlvl2(newUser);
			return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}
	}

	/*
	 * @GetMapping
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<ProductResponse> getAllProducts()
	 * { return productService.getAllProducts(); }
	 */

}

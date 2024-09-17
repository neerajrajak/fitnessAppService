package com.fitapp.services.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(value = "equipment_master")
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
	public enum AvailableWith {
	    TRAINER,
	    SEND_FROM_HQ,
	    CLASS;
	}
	
	@Id
	private String id;
	
	private AvailableWith availableWith;
	private double equipmentQuantity;
	private String equipmentName;
	
}

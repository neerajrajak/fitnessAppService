package com.fitapp.services.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedEquipment {
	private String equipmentType;
	private double equipmentQuantity;
	private String equipmentName;
	private double received;
	private double damaged;
	private double notReceived;
	private boolean checked;
}

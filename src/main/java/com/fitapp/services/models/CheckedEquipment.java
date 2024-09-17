package com.fitapp.services.models;

import com.fitapp.services.models.Equipment.AvailableWith;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedEquipment {
	private AvailableWith availableWith;
	private double equipmentQuantity;
	private String equipmentName;
	private double received;
	private double damaged;
	private double notReceived;
	private boolean checked;
}

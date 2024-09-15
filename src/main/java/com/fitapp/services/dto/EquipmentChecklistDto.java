package com.fitapp.services.dto;

import java.util.List;

import com.fitapp.services.models.CheckedEquipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentChecklistDto {
	
	List<CheckedEquipment> CheckedEquipment;
	private String sessionId;
	private String checkedByUserName;
	private String checkedByUserId;
	
}

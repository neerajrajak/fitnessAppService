package com.fitapp.services.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(value = "equipment_checklist")
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentChecklist {

	@Id
	private String id;
	List<CheckedEquipment> CheckedEquipment;
	private String sessionId;
	private String checkedByUserName;
	private String checkedByUserId;
}

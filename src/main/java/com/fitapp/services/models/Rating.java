package com.fitapp.services.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "rating")
public class Rating {
	@Id
	private String id;
	private String entityId;
	private String entityType;
	private String sessionId;
	private double rating;
	private String remark;

}

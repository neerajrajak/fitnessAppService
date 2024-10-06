package com.fitapp.services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

	@NotNull(message = "EntityId can't be null")
	@NotBlank
	private String entityId;
	@NotNull(message = "SessionId can't be null")
	@NotBlank
	private String sessionId;
	@NotNull(message = "Entity Type can't be null")
	@NotBlank
	private String entityType;
	private double rating;
	private String remark;

}

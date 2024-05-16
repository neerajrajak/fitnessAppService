package com.fitapp.services.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "societydbnum")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocietyDbNum {

	@Id
	private ObjectId id;

	public long seq;

	public SocietyDbNum(long seq) {
		this.seq = seq;
	}

}
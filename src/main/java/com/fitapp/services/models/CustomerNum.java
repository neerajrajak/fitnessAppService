package com.fitapp.services.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "customernum")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerNum {

	@Id
	private ObjectId id;

	private long seq;

	public CustomerNum(long seq) {
		this.seq = seq;
	}

}
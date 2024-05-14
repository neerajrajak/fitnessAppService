package com.fitapp.services.models;

import org.bson.types.ObjectId;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class HousingSocietyNum {

	@Id
	private ObjectId id;

	public long seq;

	public HousingSocietyNum(long seq) {
		this.seq = seq;
	}

}
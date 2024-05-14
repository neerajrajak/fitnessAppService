package com.fitapp.services.models;

import org.bson.types.ObjectId;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserNum {

	@Id
	private ObjectId id;

	public long seq;

	public UserNum(long seq) {
		this.seq = seq;
	}

}
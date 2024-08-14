package com.fitapp.services.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "clientrecordnum")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRecordNum {
	@Id
	private String id;

	private long seq;

	public ClientRecordNum(long seq) {
		this.seq = seq;
	}
}

package com.fitapp.services.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassInfo {

	private List<String> clientItems;
	private List<String> focus;
	private List<String> primaryFocus;
	private List<String> secondaryFocus;

}

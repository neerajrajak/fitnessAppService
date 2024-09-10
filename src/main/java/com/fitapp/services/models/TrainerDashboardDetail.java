package com.fitapp.services.models;

import java.util.List;

import lombok.Data;

@Data
public class TrainerDashboardDetail {

	List<SessionDetails> sessionDetail;
	private int customerTrained;
	private int trainingTime;
	private int totalTimelyAttendance;
	private int totalReferral;
}

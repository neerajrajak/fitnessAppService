package com.fitapp.services.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitapp.services.constants.FitAppConstants;
import com.fitapp.services.dto.AttendedSession;
import com.fitapp.services.dto.ClientAttendanceInfo;
import com.fitapp.services.dto.ClientInfo;
import com.fitapp.services.dto.ClientRecordDto;
import com.fitapp.services.dto.EquipmentChecklistDto;
import com.fitapp.services.dto.MarkAttendance;
import com.fitapp.services.dto.RatingDto;
import com.fitapp.services.dto.SessionDetailRequest;
import com.fitapp.services.dto.SessionRequest;
import com.fitapp.services.dto.TainerNotesDto;
import com.fitapp.services.exception.EquipmentException;
import com.fitapp.services.exception.NumberNotFoundException;
import com.fitapp.services.exception.SessionException;
import com.fitapp.services.models.ClientRecord;
import com.fitapp.services.models.ClientSessionDetails;
import com.fitapp.services.models.CustomerNum;
import com.fitapp.services.models.Equipment;
import com.fitapp.services.models.Equipment.AvailableWith;
import com.fitapp.services.models.EquipmentChecklist;
import com.fitapp.services.models.Rating;
import com.fitapp.services.models.SessionDestailNum;
import com.fitapp.services.models.SessionDetails;
import com.fitapp.services.models.TrainerDashboardDetail;
import com.fitapp.services.models.TrainerDetails;
import com.fitapp.services.models.TrainerNotes;
import com.fitapp.services.repository.ClientRecordNumRepository;
import com.fitapp.services.repository.ClientRecordRepository;
import com.fitapp.services.repository.ClientSessionDetailsRepository;
import com.fitapp.services.repository.EquipmentChecklistRepository;
import com.fitapp.services.repository.EquipmentRepository;
import com.fitapp.services.repository.RatingRepository;
import com.fitapp.services.repository.SessionDestailNumRepository;
import com.fitapp.services.repository.SessionDetailsRepositpry;
import com.fitapp.services.repository.TrainerDetailsRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

	private final MongoTemplate mongoTemplate;

	private final SessionDetailsRepositpry sessionDetailsRepositpry;

	private final SessionDestailNumRepository sessionDestailNumRepository;

	private final TrainerDetailsRepository trainerDetailsRepository;

	private final RatingRepository ratingRepository;

	private ObjectMapper objectMapper;

	private ModelMapper modelMapper;

	private final ClientRecordNumRepository clientRecordNumRepository;

	private final ClientRecordRepository clientRecordRepository;

	private final EquipmentRepository equipmentRepository;

	private final EquipmentChecklistRepository equipmentChecklistRepository;
	
	private final ClientSessionDetailsRepository clientSessionDetailsRepository;

	@PostConstruct
	public void configObjectMapper() {
		objectMapper = new ObjectMapper();
		JavaTimeModule module = new JavaTimeModule();
		objectMapper.registerModule(module);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.modelMapper = new ModelMapper();
	}

	public SessionDetails createNewSession(SessionRequest sessionRequest) {
		SessionDetails sessionDetails = objectMapper.convertValue(sessionRequest, SessionDetails.class);
		sessionDetails.setSessionId(StringUtils.leftPad(String.valueOf(getNextSessionId()), 4, "0"));
		sessionDetails = sessionDetailsRepositpry.save(sessionDetails);
		log.info("session {} is saved: ", sessionDetails.getSessionId());
		return sessionDetails;
	}

	public long getNextSessionId() {
		SessionDestailNum last = sessionDestailNumRepository.findTopByOrderByIdDesc();
		SessionDestailNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, SessionDestailNum.class);
		} else {
			last = new SessionDestailNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new SessionDestailNum(updatedSeq);
			sessionDestailNumRepository.save(next);
		}

		next = new SessionDestailNum(updatedSeq);
		return next.getSeq();
	}

	public SessionDetails getSessionDetails(String sessionId) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(sessionId);
		if (sessionDetails == null) {
			throw new NumberNotFoundException(FitAppConstants.SESSION_NOT_FOUND);
		}
		List<String> clientIds = sessionDetails.getClientInfo().stream().map(ClientInfo::getClientId).toList();
		Map<String, ClientInfo> clientInfoMap = sessionDetails.getClientInfo().stream()
				.collect(Collectors.toMap(ClientInfo::getClientId, Function.identity()));
		List<ClientRecord> clientRecordList = clientRecordRepository.findAllByClientIdIn(clientIds);
		Map<String, Double> map = new HashMap<>();
		double pregnancyCareCount = 0.0;
		double starterCount = 0.0;
		double ninjaCount = 0.0;
		double eliteCount = 0.0;
		double maleCount = 0.0;
		double femaleCount = 0.0;
		double seniorCount = 0.0;
		double healthIsuueCount = 0.0;

		for (ClientRecord clientRecord : clientRecordList) {
			if (clientRecord.getIssues() != null && clientRecord.getIssues().get("pregnecyIsuee") != null) {
				pregnancyCareCount++;
			}
			if ("Beginner".equalsIgnoreCase(clientRecord.getFitnessLevel())) {
				starterCount++;
			} else if ("Intermediate".equalsIgnoreCase(clientRecord.getFitnessLevel())) {
				ninjaCount++;
			} else if ("Advanced".equalsIgnoreCase(clientRecord.getFitnessLevel())) {
				eliteCount++;
			}

			if ("Male".equalsIgnoreCase(clientRecord.getClientGender())) {
				maleCount++;
			} else if ("Female".equalsIgnoreCase(clientRecord.getClientGender())) {
				femaleCount++;
			}
			if (clientInfoMap.get(clientRecord.getClientId()) != null) {
				if (StringUtils.isNotBlank(clientInfoMap.get(clientRecord.getClientId()).getMedicalIssue())) {
					healthIsuueCount++;
				}
			}
			int age = Integer.parseInt(clientRecord.getClientAge());
			if (age > 50) {
				seniorCount++;
			}
		}

		map.put("All", Double.valueOf(clientIds.size()));
		map.put("pregnancyCareCount", pregnancyCareCount);
		map.put("starterCount", starterCount);
		map.put("ninjaCount", ninjaCount);
		map.put("eliteCount", eliteCount);
		map.put("maleCount", maleCount);
		map.put("femaleCount", femaleCount);
		map.put("seniorCount", seniorCount);
		map.put("healthIsuueCount", healthIsuueCount);
		sessionDetails.setClientCount(map);
		List<SessionDetails> trainersAllSession = sessionDetailsRepositpry
				.findAllByTrainerIdOrderByActualEndTimeDesc(sessionDetails.getTrainerId());
		trainersAllSession.removeIf(session -> !"Close".equals(session.getStatus())
				|| sessionDetails.getSessionId().equals(session.getSessionId()));
		if (!ObjectUtils.isEmpty(trainersAllSession)) {
			sessionDetails.setPreviousWorkout(trainersAllSession.get(0).getTrainingName());
			sessionDetails.setPreviousWorkoutTime(trainersAllSession.get(0).getActualEndTime());

		}
		return sessionDetails;
	}

	public TrainerDashboardDetail getTrainerSessionDetail(SessionDetailRequest request) {
		Collections.sort(request.getDate());
		LocalDateTime startDate = LocalDateTime.of(request.getDate().get(0).toLocalDate(), LocalTime.MIDNIGHT);
		TrainerDashboardDetail trainerDashboardDetail = new TrainerDashboardDetail();
		LocalDateTime endDate = LocalDateTime.of(request.getDate().get(request.getDate().size() - 1).toLocalDate(),
				LocalTime.MAX);
		List<SessionDetails> sessionDetails = sessionDetailsRepositpry
				.findAllByTrainerIdAndStartTimeBetweenOrderByStartTimeDesc(request.getTrainerId(), startDate, endDate);
		trainerDashboardDetail.setSessionDetail(sessionDetails);
		int customerTrained = 0;
		int trainingTime = 0;
		int totalTimelyAttendance = 0;
		List<SessionDetails> allSessionDetails = sessionDetailsRepositpry.findAllByTrainerId(request.getTrainerId());
		for (SessionDetails session : allSessionDetails) {
			trainingTime += session.getTotalhours();
			customerTrained += session.getClientInfo().size();

		}

		trainerDashboardDetail.setTrainingTime(trainingTime);
		trainerDashboardDetail.setCustomerTrained(customerTrained);
		trainerDashboardDetail.setTotalTimelyAttendance(totalTimelyAttendance);
		return trainerDashboardDetail;
	}

	public ClientRecord getClientDetail(String clientId) {
		ClientRecord clientRecord = clientRecordRepository.findByClientId(clientId);
		if (clientRecord != null) {
			return clientRecord;
		}
		return null;
	}

	public ClientRecord addAndUpdateClientDetails(ClientRecordDto clientRecordDto) {
		ClientRecord clientRecord = objectMapper.convertValue(clientRecordDto, ClientRecord.class);
		ClientRecord clientDetail = clientRecordRepository.findByClientId(clientRecordDto.getClientId());
		if (clientDetail != null) {
			clientDetail = modelMapper.map(clientRecordDto, ClientRecord.class);
			return clientRecordRepository.save(clientDetail);
		}
		if (clientRecord.getClientRecordId() == null) {
			clientRecord.setClientRecordId(StringUtils.leftPad(String.valueOf(getNextClientRecordId()), 4, "0"));
		}
		clientRecord = clientRecordRepository.save(clientRecord);
		log.info("session {} is saved: ", clientRecord.getSessionId());
		return clientRecord;
	}

	public long getNextClientRecordId() {
		CustomerNum last = clientRecordNumRepository.findTopByOrderByIdDesc();
		CustomerNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, CustomerNum.class);
		} else {
			last = new CustomerNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new CustomerNum(updatedSeq);
			clientRecordNumRepository.save(next);
		}
		next = new CustomerNum(updatedSeq);
		return next.getSeq();
	}

	@Transactional
	public boolean MarkAttendance(MarkAttendance attendance) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(attendance.getSessionId());
		List<String> clientIds = attendance.getClientAttendance().stream().map(ClientAttendanceInfo::getClientId)
				.collect(Collectors.toList());
		if (sessionDetails.getClientAttendance() == null) {
			sessionDetails.setClientAttendance(attendance.getClientAttendance());
		} else {
			sessionDetails.getClientAttendance().addAll(attendance.getClientAttendance());
		}
		List<ClientRecord> clientRecordList = clientRecordRepository
				.findBySessionIdAndClientIdIn(attendance.getSessionId(), clientIds);
		Map<String, ClientAttendanceInfo> ClientAttendanceInfoMap = attendance.getClientAttendance().stream()
				.collect(Collectors.toMap(ClientAttendanceInfo::getClientId, Function.identity()));

		for (ClientRecord clientRecord : clientRecordList) {
			AttendedSession attendedSession = new AttendedSession();
			ClientAttendanceInfo clientAttendanceInfo = ClientAttendanceInfoMap.get(clientRecord.getClientId());
			attendedSession.setSessionId(clientRecord.getSessionId());
			attendedSession.setClientLocation(clientAttendanceInfo.getClientImg());
			attendedSession.setClientImg(clientAttendanceInfo.getClientLocation());
			if (clientRecord.getSessionAttended() == null) {
				List<AttendedSession> attendedSessionList = new ArrayList<>();
				attendedSessionList.add(attendedSession);
				clientRecord.setSessionAttended(attendedSessionList);
			} else {
				clientRecord.getSessionAttended().add(attendedSession);
			}
		}
		clientRecordRepository.saveAll(clientRecordList);
		sessionDetailsRepositpry.save(sessionDetails);
		return true;
	}

	public SessionDetails startAndEndSession(String sessionId, String state) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(sessionId);
		if (sessionDetails == null) {
			throw new NumberNotFoundException(FitAppConstants.SESSION_NOT_FOUND);
		}
		if ("start".equals(state)) {
			sessionDetails.setActualStartTime(LocalDateTime.now());
			if (LocalDateTime.now().isAfter(sessionDetails.getStartTime())) {
				sessionDetails.setStatus("RunningLate");
			} else {
				sessionDetails.setStatus("Active");
			}
		} else if ("end".equals(state)) {
			sessionDetails.setActualEndTime(LocalDateTime.now());
			sessionDetails.setActualTotalhours(
					Duration.between(sessionDetails.getActualStartTime(), sessionDetails.getActualEndTime()).toHours());
			sessionDetails.setStatus("Close");
		}
		sessionDetails = sessionDetailsRepositpry.save(sessionDetails);
		return sessionDetails;
	}

	public SessionDetails markRunningLate(String sessionId, String timing) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(sessionId);
		if (sessionDetails == null) {
			throw new NumberNotFoundException(FitAppConstants.SESSION_NOT_FOUND);
		}
		sessionDetails.setStatus("RunningLate");
		sessionDetails.setRunningLateTime(timing);
		sessionDetails = sessionDetailsRepositpry.save(sessionDetails);
		return sessionDetails;
	}

	public ClientRecord addTrainerNotes(TainerNotesDto tainerNotesDto) {
		ClientRecord clientRecord = clientRecordRepository.findByClientId(tainerNotesDto.getClientId());
		TrainerDetails trainerDetail = trainerDetailsRepository
				.findByTrainerId(tainerNotesDto.getTrainerNotes().getTrainerId());
		if (clientRecord.getTrainerNotes() == null) {
			ArrayList<TrainerNotes> list = new ArrayList<>();
			list.add(tainerNotesDto.getTrainerNotes());
			clientRecord.setTrainerNotes(list);
		} else {
			if (trainerDetail != null) {
				tainerNotesDto.getTrainerNotes().setTrainerSpeciality(trainerDetail.getSpeciality());
			}
			clientRecord.getTrainerNotes().add(tainerNotesDto.getTrainerNotes());
		}
		clientRecord = clientRecordRepository.save(clientRecord);
		return clientRecord;
	}

	public EquipmentChecklist updateCheckList(EquipmentChecklistDto equipmentChecklistDto) {

		EquipmentChecklist equipmentChecklist = objectMapper.convertValue(equipmentChecklistDto,
				EquipmentChecklist.class);
		EquipmentChecklist checkList = equipmentChecklistRepository
				.findBySessionId(equipmentChecklistDto.getSessionId());
		if (checkList != null) {
			throw new EquipmentException(FitAppConstants.CHECKLIST_ALREADY_UPDATED);

		}
		equipmentChecklist = equipmentChecklistRepository.save(equipmentChecklist);
		return equipmentChecklist;
	}

	public Map<AvailableWith, List<Equipment>> getEquipment() {
		List<Equipment> equipmentList = equipmentRepository.findAll();
		Map<AvailableWith, List<Equipment>> result = new HashMap<>();
		result.put(AvailableWith.TRAINER,
				equipmentList.stream().filter(eqp -> AvailableWith.TRAINER.equals(eqp.getAvailableWith())).toList());
		result.put(AvailableWith.SEND_FROM_HQ, equipmentList.stream()
				.filter(eqp -> AvailableWith.SEND_FROM_HQ.equals(eqp.getAvailableWith())).toList());
		result.put(AvailableWith.CLASS,
				equipmentList.stream().filter(eqp -> AvailableWith.CLASS.equals(eqp.getAvailableWith())).toList());
		return result;
	}

	public Equipment addEquipment(Equipment equipment) {
		return equipmentRepository.save(equipment);
	}

	public List<Rating> submitRating(List<RatingDto> rating) throws SessionException {
		if (ObjectUtils.isEmpty(rating)
				|| rating.stream().map(RatingDto::getSessionId).distinct().toList().size() > 1) {
			throw new SessionException("Invalid Session Id");
		}
		List<Rating> ratingList = ratingRepository.findAllBySessionId(rating.get(0).getSessionId());

		Map<String, Rating> collect = ratingList.stream()
				.collect(Collectors.toMap(Rating::getEntityId, Function.identity()));

		List<Rating> list = rating.stream().map(element -> modelMapper.map(element, Rating.class))
				.collect(Collectors.toList());
		List<Rating> result = new ArrayList<>();
		for (Rating rate : list) {
			Rating entityRating = collect.get(rate.getEntityId());
			if (entityRating == null) {
				result.add(ratingRepository.save(entityRating));
			}
		}
		return result;
	}

	public SessionDetails getClientSessionDetails(String sessionId) {
		SessionDetails sessionDetails = sessionDetailsRepositpry.findBySessionId(sessionId);
		if (sessionDetails == null) {
			throw new NumberNotFoundException(FitAppConstants.SESSION_NOT_FOUND);
		}
		return sessionDetails;
	}

	public List<ClientSessionDetails> getClientSessionDetail(SessionDetailRequest request) {
		Collections.sort(request.getDate());
		LocalDateTime startDate = LocalDateTime.of(request.getDate().get(0).toLocalDate(), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(request.getDate().get(request.getDate().size() - 1).toLocalDate(),
				LocalTime.MAX);
		List<ClientSessionDetails> sessionDetails = clientSessionDetailsRepository
				.findAllByTrainerIdAndStatusAndStartTimeBetweenOrderByStartTimeDesc(request.getClientId(),"Active", startDate, endDate);
		return sessionDetails;
	}
}

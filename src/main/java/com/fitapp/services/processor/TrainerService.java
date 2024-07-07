package com.fitapp.services.processor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fitapp.services.dto.NewTrainerRequest;
import com.fitapp.services.models.CustomerNum;
import com.fitapp.services.models.TrainerDbNum;
import com.fitapp.services.models.TrainerDetails;
import com.fitapp.services.repository.TrainerDbNumRepository;
import com.fitapp.services.repository.TrainerDetailsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerService {

	private final MongoTemplate mongoTemplate;

	private final TrainerDetailsRepository trainerDetailsRepository;

	private final TrainerDbNumRepository trainerDbNumRepository;

	public TrainerDetails registerNewTrainer(NewTrainerRequest trainerRequest) {
		TrainerDetails existingTrainer = trainerDetailsRepository.findByMobileNo(trainerRequest.getMobileNo());

		if (null != existingTrainer) {
			return null;
		} else {
			TrainerDetails trainerDetails = new TrainerDetails();
			trainerDetails.setTrainerId(StringUtils.leftPad(String.valueOf(getNextTrainerId()), 4, "0"));
			trainerDetails.setGender(trainerRequest.getGender());
			trainerDetails.setMobileNo(trainerRequest.getMobileNo());
			trainerDetails.setSpeciality(trainerRequest.getSpeciality());
			trainerDetails.setTrainerSince(trainerRequest.getTrainerSince());

			trainerDetailsRepository.save(trainerDetails);

			log.info("Trainer {} is saved: ", trainerDetails.getTrainerId());

			return trainerDetails;
		}

	}

	public long getNextTrainerId() {
		TrainerDbNum last = trainerDbNumRepository.findTopByOrderByIdDesc();
		TrainerDbNum next;
		long updatedSeq = 0;
		if (last != null) {
			updatedSeq = last.getSeq() + 1;
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(last.getId()));
			Update updateDefination = new Update();
			updateDefination.set("seq", updatedSeq);
			mongoTemplate.findAndModify(query, updateDefination, CustomerNum.class);
		} else {
			last = new TrainerDbNum(0);
			updatedSeq = last.getSeq() + 1;
			next = new TrainerDbNum(updatedSeq);
			trainerDbNumRepository.save(next);
		}

		next = new TrainerDbNum(updatedSeq);
		return next.getSeq();
	}

}

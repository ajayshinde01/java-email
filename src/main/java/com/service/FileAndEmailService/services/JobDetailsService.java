package com.service.FileAndEmailService.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.FileAndEmailService.entity.JobDetails;
import com.service.FileAndEmailService.entity.QrtzJobDetail;
import com.service.FileAndEmailService.exception.EmailException;
import com.service.FileAndEmailService.repository.QrtzJobDetailRepo;

@Service
public class JobDetailsService {

	@Autowired
	private QrtzJobDetailRepo qrtzJobDetailRepo;

	public ResponseEntity<?> getjobDetails(Pageable pageable) throws EmailException {
		List<JobDetails> jobDetailsList = new ArrayList<>();

		Page<QrtzJobDetail> qrtz_job_details = qrtzJobDetailRepo.findAll(pageable);
		if (!qrtz_job_details.hasContent()) {
			return new ResponseEntity<>(qrtz_job_details, HttpStatus.OK);
		}

		for (QrtzJobDetail jobDetails : qrtz_job_details.getContent()) {
			byte[] serializedData = jobDetails.getJob_data();
			try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(serializedData);
					ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream)) {
				Object deserializedObject = objectInputStream.readObject();
				JobDetails jobDetailObj = jobDetailsConverter(deserializedObject);
				jobDetailsList.add(jobDetailObj);
			} catch (IOException | ClassNotFoundException e) {
				throw new EmailException("Error while deserializing data");
			}
		}
		;
		return new ResponseEntity<>(new PageImpl<>(jobDetailsList, pageable, jobDetailsList.size()), HttpStatus.OK);

	}

	public JobDetails jobDetailsConverter(Object deserializedObject) throws EmailException {
		if (deserializedObject instanceof JobDataMap) {
			JobDataMap jobDataMap = (JobDataMap) deserializedObject;

			JobDetails qrtzJobDetails = new JobDetails();
			qrtzJobDetails.setCc((String[]) jobDataMap.get("cc"));
			qrtzJobDetails.setFileName((String) jobDataMap.get("fileName"));
			qrtzJobDetails.setSubject((String) jobDataMap.get("subject"));
			qrtzJobDetails.setTo((String[]) jobDataMap.get("to"));
			qrtzJobDetails.setBody((String) jobDataMap.get("body"));

			return qrtzJobDetails;
		} else {
			throw new EmailException("Deserialized object is not a JobDataMap");
		}
	}
}
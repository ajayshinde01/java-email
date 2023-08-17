package com.service.FileAndEmailService.services;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.service.FileAndEmailService.dto.CustomTemplateDto;
import com.service.FileAndEmailService.entity.MailData;
import com.service.FileAndEmailService.entity.MailResponse;
import com.service.FileAndEmailService.exception.EmailException;

import freemarker.template.TemplateException;

@Service
public class EmailScheduleService {

	@Autowired
	private Scheduler scheduler;

	public ResponseEntity<MailResponse> schedulEmail(MailData mailRequest, MultipartFile[] file)
			throws IOException, TemplateException, EmailException {

		try {
			ZonedDateTime dateTime = ZonedDateTime.of(mailRequest.getDateTime(), mailRequest.getTimeZone());
			if (dateTime.isBefore(ZonedDateTime.now())) {
				MailResponse mailResponse = new MailResponse(false, "dateTime must be after current time");
				return ResponseEntity.badRequest().body(mailResponse);
			}
			JobDetail jobDetail = buildJobDetail(mailRequest, file);
			Trigger trigger = buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);

			MailResponse mailResponse = new MailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
			return ResponseEntity.ok(mailResponse);
		} catch (SchedulerException ex) {
			MailResponse mailResponse = new MailResponse(false, "Error scheduling email. Please try later!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mailResponse);
		}

	}

	private JobDetail buildJobDetail(MailData mailRequest, MultipartFile[] file)
			throws IOException, TemplateException, EmailException {
		JobDataMap jobDataMap = new JobDataMap();
		if (mailRequest.getTo() == null) {
			throw new EmailException("Email address is Mandatory");
		}
		jobDataMap.put("to", mailRequest.getTo());
		jobDataMap.put("cc", mailRequest.getCc());
		jobDataMap.put("subject", mailRequest.getSubject());
		jobDataMap.put("body", mailRequest.getBody());

		if (file != null) {
			jobDataMap.put("fileLength", file.length);
			for (int i = 0; i < file.length; i++) {
				jobDataMap.put("fileName " + i, file[i].getOriginalFilename());
				try {
					byte[] fileContent = file[i].getBytes();
					jobDataMap.put("file " + i, fileContent);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return JobBuilder.newJob(EmailJob.class).withIdentity(UUID.randomUUID().toString(), "email-jobs")
				.withDescription("Send Email Job").usingJobData(jobDataMap).storeDurably().build();
	}

	private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		return TriggerBuilder.newTrigger().forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), "email-triggers").withDescription("Send Email Trigger")
				.startAt(Date.from(startAt.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()).build();
	}

	public CustomTemplateDto templateMailSchedule(Long templateId, Map<String, Object> temlateDataField) {

		return null;
	}
}

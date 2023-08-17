package com.service.FileAndEmailService.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.service.FileAndEmailService.entity.EmailRequest;
import com.service.FileAndEmailService.entity.MailData;
import com.service.FileAndEmailService.entity.MailResponse;
import com.service.FileAndEmailService.exception.EmailException;
import com.service.FileAndEmailService.services.CustomTemplateSchedular;
import com.service.FileAndEmailService.services.EmailScheduleService;
import com.service.FileAndEmailService.services.JobDetailsService;

import freemarker.template.TemplateException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class EmailJobSchedulerController {
	@Autowired
	private EmailScheduleService emailScheduleService;

	@Autowired
	private CustomTemplateSchedular customTemplateSchedular;

	@Autowired
	private JobDetailsService jobDetailsService;

	@PostMapping("/send")
	public ResponseEntity<MailResponse> scheduleEmail(@Valid @RequestPart MailData mailRequest,
			@RequestPart(required = false) MultipartFile[] file) throws IOException, TemplateException, EmailException {
		return emailScheduleService.schedulEmail(mailRequest, file);
	}

	@PostMapping("/schedule")
	public ResponseEntity<?> scheduleMailWithTemplateID(@Valid @RequestBody EmailRequest emailRequest,
			@RequestPart(required = false) MultipartFile[] file) throws IOException, TemplateException, EmailException {
		return customTemplateSchedular.mailScheduleUsingTemplateID(emailRequest, file);

	}

	@GetMapping("/get")
	public ResponseEntity<?> getMailScheduleData(
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,

			@PageableDefault(page = 0, size = 1) Pageable pageable) throws EmailException {
		return jobDetailsService.getjobDetails(pageable);
	}
}

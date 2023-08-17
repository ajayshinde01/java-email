package com.service.FileAndEmailService.services;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.service.FileAndEmailService.entity.CustomTemplate;
import com.service.FileAndEmailService.entity.EmailRequest;
import com.service.FileAndEmailService.entity.MailData;
import com.service.FileAndEmailService.exception.EmailException;
import com.service.FileAndEmailService.exception.TemplateNotFound;
import com.service.FileAndEmailService.repository.CustomTemplateRepo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.validation.Valid;

@Service
public class CustomTemplateSchedular {
	@Autowired
	private EmailScheduleService emailScheduleService;
	@Autowired
	private CustomTemplateRepo customTemplateRepo;
	@Autowired
	private CustomTemplateService customTemplateService;

	private Configuration configuration;

	public ResponseEntity<?> mailScheduleUsingTemplateID(@Valid EmailRequest emailRequest, MultipartFile[] file)
			throws IOException, TemplateException, EmailException {

		Long id = emailRequest.getId();
		String emailTo = emailRequest.getEmail();

		String[] cc = emailRequest.getCc();
		String templateHTML = readyTemplate(id, emailRequest.getData());
		String subject = customTemplateService.getTemplate(id).getSubject();
		LocalDateTime time = LocalDateTime.now().plusSeconds(30);
		ZoneId zoneId = ZoneId.systemDefault();

		MailData mailData = new MailData(new String[] { emailTo }, subject, templateHTML, time, zoneId, cc);

		return emailScheduleService.schedulEmail(mailData, file);

	}

	public String readyTemplate(Long id, Map<String, String> templateData) throws IOException, TemplateException {
		CustomTemplate template = customTemplateRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new TemplateNotFound("Template not found"));
		String htmlContent = template.getContent();
		Template t = null;

		t = new Template("templateName", new StringReader(htmlContent), configuration);

		Writer out = new StringWriter();

		t.process(templateData, out);

		String readyTemplate = null;

		readyTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(t, templateData);

		return readyTemplate;

	}

}

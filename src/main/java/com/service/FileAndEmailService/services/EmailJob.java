package com.service.FileAndEmailService.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.service.FileAndEmailService.exception.EmailException;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailProperties mailProperties;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
		String subject = jobDataMap.getString("subject");
		String body = jobDataMap.getString("body");
		String[] recipientToEmail = (String[]) jobDataMap.get("to");
		String[] recipientCcEmail = (String[]) jobDataMap.get("cc");

		int length = 0;
		String contentType = null;
		byte[][] file = null;
		String[] fileName = null;
		if(jobDataMap.containsKey("file 0")) {
			 length = jobDataMap.getInt("fileLength");
			 file = new byte[length][];
			 fileName = new String[length];
			
		}
		if (length > 0) {

			for (int i = 0; i < length; i++) {
				fileName[i] = jobDataMap.getString("fileName " + i);
				file[i] = (byte[]) jobDataMap.get("file " + i);

				// Use URLConnection to get the content type based on the file extension
				contentType = URLConnection.guessContentTypeFromName(fileName[i]);

				// If URLConnection couldn't guess the content type, use a default value
				if (contentType == null) {
					contentType = "application/octet-stream";
				}
			}
		}

		try {
			sendMail(mailProperties.getUsername(), recipientToEmail, recipientCcEmail, subject, body, file, fileName,
					contentType);
		} catch (MessagingException e) {
			System.out.println("Inside");
			
			e.getStackTrace();
		}

	}

	private void sendMail(String fromEmail, String[] toEmail, String[] ccEmail, String subject, String body,
			byte[][] file, String[] filename, String contentType) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.toString());
		messageHelper.setSubject(subject);
		messageHelper.setText(body, true);
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(toEmail);
		if (ccEmail != null && ccEmail.length > 0) {
			messageHelper.setCc(ccEmail);
		}
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				int no = i;
				DataSource dataSource = new DataSource() {

					@Override
					public InputStream getInputStream() throws IOException {
						return new ByteArrayInputStream(file[no]);
					}

					@Override
					public OutputStream getOutputStream() throws IOException {
						throw new UnsupportedOperationException("Read-only data source");
					}

					@Override
					public String getContentType() {
						return contentType;
					}

					@Override
					public String getName() {
						return filename[no];
					}

				};

				messageHelper.addAttachment(dataSource.getName(), dataSource);
			}
		}
		mailSender.send(message);
	}

}

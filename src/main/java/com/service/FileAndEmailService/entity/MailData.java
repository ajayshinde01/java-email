package com.service.FileAndEmailService.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailData {
	
	private String[] to;
	private String subject;
	
	@NotNull(message = "Email Body must not be null")
	@NotBlank(message = "Email Body must not be blank")
	private String body;
	private LocalDateTime dateTime;
	private ZoneId timeZone;
	private String[] cc;

}
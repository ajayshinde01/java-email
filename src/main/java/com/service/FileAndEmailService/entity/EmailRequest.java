package com.service.FileAndEmailService.entity;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmailRequest {
	
	private Long id;
	@NotNull(message = "Email must not be null")
	@NotBlank(message = "Email must not be blank")
	private String email;
	private String[] cc;
	private Map<String, String> data;
	private MultipartFile image;
}

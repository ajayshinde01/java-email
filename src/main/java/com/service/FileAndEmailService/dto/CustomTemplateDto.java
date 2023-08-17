package com.service.FileAndEmailService.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomTemplateDto {
	private Long id;
	private String name;
	private String subject;
	@NotNull(message = "content should not be empty")
	@NotBlank(message = "content should not be blank")
	private String content;
	private boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String createdBy;
	private String updatedBy;

}

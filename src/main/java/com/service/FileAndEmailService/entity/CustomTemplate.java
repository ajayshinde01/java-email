package com.service.FileAndEmailService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "custom_template")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(sequenceName = "template_seq", name = "template_seq", initialValue = 1, allocationSize = 1)
public class CustomTemplate extends Status {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_seq")
	private Long id;

	private String name;

	private String subject;

	@Column(length = 1500)
	private String content;

	public CustomTemplate(boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy,
			String updatedBy, String name, String subject, String content) {
		super(isDeleted, createdAt, updatedAt, createdBy, updatedBy);
		this.name = name;
		this.subject = subject;
		this.content = content;
	}

}

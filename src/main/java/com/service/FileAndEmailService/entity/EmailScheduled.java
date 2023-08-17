package com.service.FileAndEmailService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailScheduled {
	
	@Id
	private Long id;
	
	@Column(name = "trigger_name")
	private String triggerName;
	
	@Column(name = "job_name")
	private String jobName;
}

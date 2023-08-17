package com.service.FileAndEmailService.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qrtz_job_details")
public class QrtzJobDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "job_name")
	private String job_name;

	@Column(name = "job_data")
	private byte[] job_data;
}

package com.service.FileAndEmailService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import jakarta.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
	protected boolean isDeleted;

	@Column(name = "created_at")
	protected LocalDateTime createdAt;

	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@Column(name = "created_by")
	protected String createdBy;

	@Column(name = "updated_by")
	protected String updatedBy;

}
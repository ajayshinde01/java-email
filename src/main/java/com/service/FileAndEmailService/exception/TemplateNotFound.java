package com.service.FileAndEmailService.exception;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

public class TemplateNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

}
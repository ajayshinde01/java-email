package com.service.FileAndEmailService.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class JobDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String[] cc;
	private String fileName;
	private String subject;
	private String[] to;
	private String body;

}

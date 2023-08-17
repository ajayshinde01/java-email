package com.service.FileAndEmailService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
 @AllArgsConstructor  
 @NoArgsConstructor  
 public class MailResponse { 
	 private boolean status; 
	  private String jobId;
	  private String jobGroup;
      private String message;  
    
    
	public MailResponse(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
      
      
 } 
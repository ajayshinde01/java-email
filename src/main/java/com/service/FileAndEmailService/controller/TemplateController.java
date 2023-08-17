package com.service.FileAndEmailService.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.FileAndEmailService.dto.CustomTemplateDto;
import com.service.FileAndEmailService.exception.EmailException;
import com.service.FileAndEmailService.services.CustomTemplateService;
import com.service.FileAndEmailService.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/template/")
@CrossOrigin(origins = "*",allowedHeaders = "*")

public class TemplateController {

	@Autowired

	private CustomTemplateService customTemplateService;

	@PostMapping("/create")

	public ResponseEntity<?> createTemplate(@Valid @RequestBody CustomTemplateDto template) {

		CustomTemplateDto createdTemplate = customTemplateService.createTemplate(template);

		return ResponseEntity.ok(createdTemplate);

	}

	@GetMapping("/{id}")

	public ResponseEntity<?> getTemplate(@PathVariable("id") Long id) {

		CustomTemplateDto createdTemplate = customTemplateService.getTemplate(id);

		return ResponseEntity.ok(createdTemplate);

	}

	@PutMapping("/update")

	public ResponseEntity<?> updateTemplate(@Valid @RequestBody CustomTemplateDto template) throws EmailException {

		CustomTemplateDto createdTemplate = customTemplateService.updateTemplate(template);

		return ResponseEntity.ok(createdTemplate);

	}

	@DeleteMapping("/{id}")

	public ResponseEntity<?> deleteTemplateById(@PathVariable("id") Long id,
			@RequestParam(value = "updatedBy") String updatedBy) {

		return ResponseEntity
				.ok(new ApiResponse(customTemplateService.deleteTemplateById(id, updatedBy), LocalDateTime.now()));

	}

}

package com.service.FileAndEmailService.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.FileAndEmailService.dto.CustomTemplateDto;
import com.service.FileAndEmailService.entity.CustomTemplate;
import com.service.FileAndEmailService.exception.EmailException;
import com.service.FileAndEmailService.exception.TemplateNotFound;
import com.service.FileAndEmailService.repository.CustomTemplateRepo;

@Service

public class CustomTemplateService {

	@Autowired

	private CustomTemplateRepo customTemplateRepo;

	public CustomTemplateDto createTemplate(CustomTemplateDto template) {

		CustomTemplate templateToCreate = new CustomTemplate(false, LocalDateTime.now(), null, template.getCreatedBy(),
				null, template.getName(), template.getSubject(), template.getContent());

		CustomTemplate templateCreated = customTemplateRepo.save(templateToCreate);

		CustomTemplateDto templateDto = new CustomTemplateDto(templateCreated.getId(), templateCreated.getName(),
				templateCreated.getSubject(), templateCreated.getContent(), templateCreated.isDeleted(),
				templateCreated.getCreatedAt(), templateCreated.getUpdatedAt(), templateCreated.getCreatedBy(),
				templateCreated.getUpdatedBy());

		return templateDto;

	}

	public CustomTemplateDto getTemplate(Long id) {

		CustomTemplate template = customTemplateRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new TemplateNotFound("Template not found"));

		CustomTemplateDto templateDto = new CustomTemplateDto(template.getId(), template.getName(),
				template.getSubject(), template.getContent(), template.isDeleted(), template.getCreatedAt(),
				template.getUpdatedAt(), template.getCreatedBy(), template.getUpdatedBy());

		return templateDto;

	}

	public CustomTemplateDto updateTemplate(CustomTemplateDto template) throws EmailException {
		if (template.getId() == null) {
			throw new EmailException("Id should not be null");
		}

		CustomTemplate templateToUpdate = customTemplateRepo.findByIdAndIsDeletedFalse(template.getId())
				.orElseThrow(() -> new TemplateNotFound("Template not found"));

		templateToUpdate.setName(template.getName());

		templateToUpdate.setSubject(template.getSubject());

		templateToUpdate.setContent(template.getContent());

		templateToUpdate.setUpdatedAt(LocalDateTime.now());

		templateToUpdate.setUpdatedBy(template.getUpdatedBy());

		CustomTemplate templateUpdated = customTemplateRepo.save(templateToUpdate);

		CustomTemplateDto templateDto = new CustomTemplateDto(templateUpdated.getId(), templateUpdated.getName(),
				templateUpdated.getSubject(), templateUpdated.getContent(), templateUpdated.isDeleted(),
				templateUpdated.getCreatedAt(), templateUpdated.getUpdatedAt(), templateUpdated.getCreatedBy(),
				templateUpdated.getUpdatedBy());

		return templateDto;

	}

	public String deleteTemplateById(Long id, String updatedBy) {

		CustomTemplate templateToDelete = customTemplateRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new TemplateNotFound("Template not found"));

		templateToDelete.setDeleted(true);

		templateToDelete.setUpdatedAt(LocalDateTime.now());

		templateToDelete.setUpdatedBy(updatedBy);

		customTemplateRepo.save(templateToDelete);

		return "Template is deleted";

	}

}
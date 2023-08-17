package com.service.FileAndEmailService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.FileAndEmailService.entity.CustomTemplate;

@Repository

public interface CustomTemplateRepo extends JpaRepository<CustomTemplate, Long> {
	Optional<CustomTemplate> findByIdAndIsDeletedFalse(Long id);
}
	
package com.service.FileAndEmailService.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.service.FileAndEmailService.exception.FileManagerException;
import com.service.FileAndEmailService.services.FileManagerService;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class FileManagerController {
	@Autowired
	private FileManagerService fileManagerSerive;

	@PostMapping("/save")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file)
			throws IOException, FileManagerException {
		return fileManagerSerive.uploadFile(file);
	}

	@GetMapping("/get")
	public ResponseEntity<?> downloadFile(@RequestParam("file") String fileName)
			throws IOException, FileManagerException {
		return fileManagerSerive.downloadFile(fileName);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteFile(@RequestParam("file") String fileName)
			throws IOException, FileManagerException {
		return fileManagerSerive.deleteFile(fileName);

	}
}

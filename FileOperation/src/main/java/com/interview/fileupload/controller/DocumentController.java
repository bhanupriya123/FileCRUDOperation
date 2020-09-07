/**
 * 
 */
package com.interview.fileupload.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author USER
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.interview.fileupload.response.DocumentResponse;
import com.interview.fileupload.service.DocumentStorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/")
public class DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private DocumentStorageService documneStorageService;

	@ApiOperation(value = "Upload a file")
	@PostMapping("/uploadFile")
	public DocumentResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer UserId,
			@RequestParam("docType") String docType) {
		logger.debug("File is getting processed to be uploaded");
		String fileName = documneStorageService.storeFile(file, UserId, docType);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		return new DocumentResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@ApiOperation(value = "Download a file")
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam("userId") Integer userId,
			@RequestParam("docType") String docType, HttpServletRequest request) {
		String fileName = documneStorageService.getDocumentName(userId, docType);
		Resource resource = null;
		if (fileName != null && !fileName.isEmpty()) {
			try {
				logger.debug("File is getting ready to be loaded" + fileName);
				resource = documneStorageService.loadFileAsResource(fileName);
			} catch (Exception e) {
				logger.error("Something went wrong" + e.getMessage());
				e.printStackTrace();
			}
			String contentType = null;
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				// logger.info("Could not determine file type.");
			}
			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@ApiOperation(value = "Delete a file")
	@GetMapping("/deleteFile")
	public ResponseEntity<Resource> deleteFile(@RequestParam("userId") Integer userId,
			@RequestParam("docType") String docType, HttpServletRequest request) {
		String fileName = documneStorageService.getDocumentName(userId, docType);
		Resource resource = null;
		if (fileName != null && !fileName.isEmpty()) {
			try {

				logger.debug("File " + fileName + "is getting processed to be deleted");
				documneStorageService.deleteFile(fileName);
			} catch (Exception e) {
				logger.error("Something went wrong ." + e.getMessage());
				e.printStackTrace();
			}
			String contentType = null;
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				// logger.info("Could not determine file type.");
			}
			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		} else {
			logger.error("File " + fileName + "not found");
			return ResponseEntity.notFound().build();
		}
	}
}
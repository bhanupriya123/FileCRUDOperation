/**
 * 
 */
package com.interview.fileupload.service.impl;

import com.interview.fileupload.entity.Document;
import com.interview.fileupload.exception.DocumentStorageException;
import com.interview.fileupload.repo.DocumentStorageRepo;
import com.interview.fileupload.service.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author USER
 *
 */
@Service
public class DocumentStorageServiceImpl implements DocumentStorageService {
	private final Path fileStorageLocation;

	@Autowired
	DocumentStorageRepo docRepo;

	@Autowired
	public DocumentStorageServiceImpl(Document fileStorageProperties) {

		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())

				.toAbsolutePath().normalize();

		try {

			Files.createDirectories(this.fileStorageLocation);

		} catch (Exception ex) {

			throw new DocumentStorageException(
					"Could not create the directory where the uploaded files will be stored.", ex);

		}

	}

	public String storeFile(MultipartFile file, Integer userId, String docType) {

		// Normalize file name

		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

		String fileName = "";

		try {

			// Check if the file's name contains invalid characters

			if (originalFileName.contains("..")) {

				throw new DocumentStorageException(
						"Sorry! Filename contains invalid path sequence " + originalFileName);
			}

			String fileExtension = "";
			try {
				fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			} catch (Exception e) {
				fileExtension = "";
			}
			fileName = userId + "_" + docType + fileExtension;
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			Document doc = docRepo.checkDocumentByUserId(userId, docType);
			if (doc != null) {
				doc.setDocumentFormat(file.getContentType());
				doc.setFileName(fileName);
				docRepo.save(doc);
			} else {
				Document newDoc = new Document();
				newDoc.setUserId(userId);
				newDoc.setDocumentFormat(file.getContentType());
				newDoc.setFileName(fileName);
				newDoc.setDocumentType(docType);
				docRepo.save(newDoc);
			}
			return fileName;
		} catch (IOException ex) {
			throw new DocumentStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) throws Exception {
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

	public String getDocumentName(Integer userId, String docType) {
		return docRepo.getUploadDocumnetPath(userId, docType);

	}

	@Override
	public void deleteFile(String fileName) {
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				try {
					Files.delete(Paths.get(fileName));
					Document document = docRepo.checkDocumentByfileName(fileName);
					docRepo.deleteById(document.getDocumentId());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}
}

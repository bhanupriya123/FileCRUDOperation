/**
 * 
 */
package com.interview.fileupload.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.interview.fileupload.entity.Document;
import com.interview.fileupload.exception.DocumentStorageException;
import com.interview.fileupload.service.DocumentStorageService;

/**
 * @author USER
 *
 */
@Service
public class DocumentStorageServiceImpl implements DocumentStorageService {
	private final Path fileStorageLocation;

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
	public Resource copyFileAsResource(String fileName) throws Exception {
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Path toFilePath = this.fileStorageLocation.resolve(fileName+"_copy").normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				Path resPath = Files.copy(filePath, toFilePath);
				Resource outputResource = new UrlResource(resPath.toUri());
				return outputResource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

	@Override
	public void deleteFile(String fileName) {
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				try {
					Files.delete(Paths.get(fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
	}
}

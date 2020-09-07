/**
 * 
 */
package com.interview.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author USER
 *
 */
public interface DocumentStorageService {

	String getDocumentName(Integer userId, String docType);

	Resource loadFileAsResource(String fileName) throws Exception;

	String storeFile(MultipartFile file, Integer userId, String docType);

	void deleteFile(String fileName) ;

}

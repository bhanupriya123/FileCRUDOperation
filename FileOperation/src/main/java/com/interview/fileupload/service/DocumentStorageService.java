/**
 * 
 */
package com.interview.fileupload.service;

import org.springframework.core.io.Resource;

/**
 * @author USER
 *
 */
public interface DocumentStorageService {
	Resource loadFileAsResource(String fileName) throws Exception;

	Resource copyFileAsResource(String fileName) throws Exception;

	void deleteFile(String fileName);

}

/**
 * 
 */
package com.interview.fileupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.interview.fileupload.entity.Document;

/**
 * @author USER
 *
 */
@Repository
public interface DocumentStorageRepo extends JpaRepository<Document, Integer> {

	@Query("Select a from Document a where user_id = ?1 and document_type = ?2")
	Document checkDocumentByUserId(Integer userId, String docType);

	@Query("Select fileName from Document a where user_id = ?1 and document_type = ?2")
	String getUploadDocumnetPath(Integer userId, String docType);
	
	@Query("Select a from Document a where file_name = ?1")
	Document checkDocumentByfileName(String fileName);
}

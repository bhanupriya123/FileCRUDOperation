/**
 * 
 */
package com.interview.fileupload.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author USER
 *
 */
@Configuration
@ConfigurationProperties(prefix = "file")

public class Document {

	@ApiModelProperty(notes = "The database generated document ID")

	private Integer documentId;

	@ApiModelProperty(notes = "The user id")
	private Integer UserId;

	@ApiModelProperty(notes = "The document name")
	private String fileName;

	@ApiModelProperty(notes = "The document type")
	private String documentType;

	@ApiModelProperty(notes = "The document format")
	private String documentFormat;

	@ApiModelProperty(notes = "The upload directory")
	private String uploadDir;

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentFormat() {
		return documentFormat;
	}

	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

}

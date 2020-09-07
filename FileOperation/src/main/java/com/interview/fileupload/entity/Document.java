/**
 * 
 */
package com.interview.fileupload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author USER
 *
 */
@Configuration
@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "merchant_documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated document ID")
    @Column(name = "document_id")
    private Integer documentId;
    
    @Column(name = "user_id")
    @ApiModelProperty(notes = "The user id")
    private Integer UserId;
    
    @Column(name = "file_name")
    @ApiModelProperty(notes = "The document name")
    private String fileName;
    
    @Column(name = "document_type")
    @ApiModelProperty(notes = "The document type")
    private String documentType;
    
    @Column(name = "document_format")
    @ApiModelProperty(notes = "The document format")
    private String documentFormat;
    
    @Column(name = "upload_dir")
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

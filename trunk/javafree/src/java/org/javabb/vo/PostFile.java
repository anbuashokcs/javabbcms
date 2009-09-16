package org.javabb.vo;

import java.io.Serializable;

/**
 * $Id: PostFile.java,v 1.1 2009/05/11 20:26:51 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
/** 
 *        @hibernate.class
 *         table="jbb_posts_files"
 *     
*/
public class PostFile implements Serializable {

	private static final long serialVersionUID = -2173807651464802903L;
	private Long fileId;
	private String fileName;
	private String filePath;
	private String fileSize;
	private String userFileName;
	private Integer downloads;
	
	private Post post;

	public PostFile(Long fileId){
		this.fileId = fileId;
	}
	
	public PostFile(){
		
	}
	

	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = PRIME * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = PRIME * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = PRIME * result + ((fileSize == null) ? 0 : fileSize.hashCode());
		result = PRIME * result + ((post == null) ? 0 : post.hashCode());
		result = PRIME * result + ((userFileName == null) ? 0 : userFileName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PostFile other = (PostFile) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (fileSize == null) {
			if (other.fileSize != null)
				return false;
		} else if (!fileSize.equals(other.fileSize))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (userFileName == null) {
			if (other.userFileName != null)
				return false;
		} else if (!userFileName.equals(other.userFileName))
			return false;
		return true;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getUserFileName() {
		return userFileName;
	}

	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}


}

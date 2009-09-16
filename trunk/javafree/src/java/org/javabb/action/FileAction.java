package org.javabb.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.PostTransaction;
import org.javabb.vo.PostFile;

public class FileAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private PostTransaction postTransaction;

	public void setPostTransaction(PostTransaction postTransaction) {
		this.postTransaction = postTransaction;
	}

	private PostFile postFile = new PostFile();

	private Long fileId;

	private byte[] attachment;

	private int bytesA;

	BufferedInputStream bis;

	public String loadFile() {
		try {
			postFile = postTransaction.loadPostFile(fileId);
			String pathName = postFile.getFilePath();
			String fileName = postFile.getFileName();
			bis = new BufferedInputStream(new FileInputStream(new File(pathName
					+ fileName)));

			bytesA = bis.available();
			attachment = new byte[bytesA];
		} catch (Exception e) {
		}

		return SUCCESS;
	}

	/**
	 * Just to manager the download
	 * 
	 * @param response
	 * @param attachment
	 */
	public void getDownloadFile(HttpServletResponse response,
			byte[] attachment, BufferedInputStream bis) {
		
		if(1==1){
			return;
		}

		try {
			ServletOutputStream op = response.getOutputStream();
			while (true) {
				int bytesRead = bis.read(attachment, 0, attachment.length);
				if (bytesRead < 0) {
					break;
				}
				op.write(attachment, 0, bytesRead);
			}
		} catch (Exception e) {
		}
	}

	public PostFile getPostFile() {
		return postFile;
	}

	public void setPostFile(PostFile postFile) {
		this.postFile = postFile;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public int getBytesA() {
		return bytesA;
	}

	public void setBytesA(int bytesA) {
		this.bytesA = bytesA;
	}

	public BufferedInputStream getBis() {
		return bis;
	}

	public void setBis(BufferedInputStream bis) {
		this.bis = bis;
	}

}

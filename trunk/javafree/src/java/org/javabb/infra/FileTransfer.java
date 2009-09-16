package org.javabb.infra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.javabb.vo.PostFile;

import sun.misc.ExtensionInstallationException;

import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * @author Dalton Camargo
 */
public class FileTransfer {

    /**
     * Classe para enviar upload através de uma action Exemplo de utilização
     * através da Action:
     * 
     * String nameFile = "arquivo_" + editorial2.getIdEditorial();
     * 
     * MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)
     * ServletActionContext.getRequest(); String arquivo =
     * FileTransfer.verificaUpload(multiWrapper,nameFile); if(arquivo != null &&
     * !arquivo.equals("")){ arquivo = "/diretorio/Uploads/" + nameFile + "." +
     * arquivo; }
     * 
     * editorial2.setImage(arquivo);
     * 
     * @param multiWrapper
     *            -
     * @param inputNameFile
     *            - Nome do arquivo a ser gravado no F.System
     * @return - Nomes dos arquivos enviados
     */
    public static ArrayList uploadFile(MultiPartRequestWrapper multiWrapper) {
	String fileName = "";
	ArrayList filesExt = new ArrayList();

	if (multiWrapper.hasErrors()) {
	    Collection errors = multiWrapper.getErrors();
	    Iterator i = errors.iterator();
	    while (i.hasNext()) {
		// obj.addActionError((String) i.next());
	    }
	} else {
	    Enumeration e = multiWrapper.getFileNames();
	    while (e.hasMoreElements()) {

		String inputValue = (String) e.nextElement();

		String contentType = multiWrapper.getContentType(inputValue);

		if (contentType != null) {
		    fileName = multiWrapper.getFilesystemName(inputValue);

		    File file = multiWrapper.getFile(inputValue);

		    String nameFile = file.getName();
		    String pathFile = file.getAbsolutePath().replaceAll(
			    nameFile, "");

		    String nameWithoutDot = nameFile.substring(0, nameFile
			    .indexOf('.'));

		    String extension = "";
		    int whereDot = nameFile.lastIndexOf('.');
		    if (0 < whereDot && whereDot <= nameFile.length() - 2) {
			extension = nameFile.substring(whereDot + 1);
		    }
		    String inputNameFile = new String(Double.toString(System
			    .currentTimeMillis()));

		    file.renameTo(new File(pathFile + nameWithoutDot + "-"
			    + inputNameFile + "." + extension));
		    filesExt.add(nameWithoutDot + "-" + inputNameFile + "."
			    + extension);
		} else {
		    // Não foi selecionado nenhum arquivo
		    filesExt.add(null);
		}

	    }
	}
	return filesExt;
    }

    public static List uploadFileRecursive(
	    MultiPartRequestWrapper multiWrapper) throws Exception {
	return uploadFileRecursive(multiWrapper, null, false);
    }

    /**
     * Class to send files through an WebWork's action
     * 
     * @param multiWrapper
     *            -
     * @param inputNameFile
     *            - Nome do arquivo a ser gravado no F.System
     * @return - HashMap contendo como chave o nome do arquivo enviado, e como
     *         valor o tamanho do arquivo
     */
    @SuppressWarnings("unchecked")
    public static List uploadFileRecursive(
	    MultiPartRequestWrapper multiWrapper, String inputFileName,
	    boolean isAvatar) throws Exception {

	List filesExt = new ArrayList();

	if (multiWrapper.hasErrors()) {
	    // Collection errors = multiWrapper.getErrors();
	    // Iterator i = errors.iterator();
	    /*
	     * while (i.hasNext()) { obj.addActionError((String) i.next()); }
	     */
	} else {
	    Enumeration e = multiWrapper.getFileNames();
	    while (e.hasMoreElements()) {

		String inputValue = (String) e.nextElement();

		String contentType = multiWrapper.getContentType(inputValue);

		if (contentType != null) {
		    String fileName = multiWrapper.getFilesystemName(inputValue);

		    File file = multiWrapper.getFile(inputValue);

		    String nameFile = file.getName();
		    String pathFile = file.getAbsolutePath().replaceAll(nameFile, "");

		    String extension = "";
		    int whereDot = nameFile.lastIndexOf('.');
		    if (0 < whereDot && whereDot <= nameFile.length() - 2) {
			extension = nameFile.substring(whereDot + 1);
		    }

		    if (isAvatar) {
			// Just JPG and GIF are alloweds
			if (!"jpg".equalsIgnoreCase(extension)
				&& !"gif".equalsIgnoreCase(extension)) {
			    deleteFile(pathFile, nameFile);
			    throw new ExtensionInstallationException(
				    "Only JPG and GIF are allowed!");
			}
		    }

		    if ("exe".equals(extension) || "scr".equals(extension) || "cmd".equals(extension)) {
			deleteFile(pathFile, nameFile);
			throw new ExtensionInstallationException("This kind of extension is not allowed!");
		    }

		    // Modified just to return the file in KB or Bytes
		    String fileLenghtReturn = "";
		    if (file.length() >= 1024) {
			fileLenghtReturn = (file.length() / 1024) + " KB";
		    } else {
			fileLenghtReturn = file.length() + " bytes";
		    }

		    String userPath = UserPath.userPath(nameFile, pathFile + "files");
		    userPath = userPath.replaceAll(nameFile, "");
		    userPath = userPath.replace('/', File.separatorChar);
		    userPath = userPath.replace('\\', File.separatorChar);
		    
		    boolean success = (new File(userPath)).mkdirs();
		    file.renameTo(new File(userPath + nameFile));
		    
		    
		    deleteFile(pathFile, nameFile);
		    
		    
		    PostFile postFile = new PostFile();
		    postFile.setFileName(nameFile);
		    postFile.setFileSize(fileLenghtReturn);
		    postFile.setFilePath(userPath);

		    filesExt.add(postFile);

		    deleteFile(pathFile, nameFile);

		}
	    }
	}
	return filesExt;
    }

    /**
     * @param multiWrapper
     * @return
     */
    @SuppressWarnings("uncheck")
    public static String getAbsolutPathName(MultiPartRequestWrapper multiWrapper) {
	String pathFile = "";
	Enumeration e = multiWrapper.getFileNames();
	while (e.hasMoreElements()) {

	    String inputValue = (String) e.nextElement();
	    String contentType = multiWrapper.getContentType(inputValue);
	    if (contentType != null) {
		String fileName = multiWrapper.getFilesystemName(inputValue);
		File file = multiWrapper.getFile(inputValue);
		String nameFile = file.getName();
		pathFile = file.getAbsolutePath().replaceAll(nameFile, "");
		break;
	    }
	}
	return pathFile;
    }

    public static boolean deleteFile(String path, String fileName) {
	try {
	    FileUtils.forceDelete(new File(path + fileName));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return true;
    }

    public static void deleteFileByList(Set set) {
	if (set != null && set.size() > 0) {
	    Iterator it = set.iterator();
	    while (it.hasNext()) {
		PostFile pFile = (PostFile) it.next();
		deleteFile(pFile.getFilePath(), pFile.getFileName());
	    }
	}
    }

    public static List uploadFiles(HttpServletRequest req, String inputFileName, boolean isAvatar) throws Exception {
	MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) req;
	return FileTransfer.uploadFileRecursive(multiWrapper, inputFileName, isAvatar);
    }

}

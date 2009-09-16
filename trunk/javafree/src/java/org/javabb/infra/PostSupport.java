package org.javabb.infra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.javabb.vo.Post;
import org.javabb.vo.PostFile;

import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;

public class PostSupport {

    /**
     * Support upload files by WebWork's action
     * 
     * @param servlet
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Set uploadPostFiles(Post post, HttpServletRequest req)
	    throws Exception {

	MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) req;
	List files = FileTransfer.uploadFileRecursive(multiWrapper);
	Set setPostFiles = new HashSet();
	
	for(int i=0; i<files.size(); i++){
	    PostFile pFile = (PostFile)files.get(i);
	    pFile.setPost(post);
	    setPostFiles.add(pFile);
	}
	return setPostFiles;
    }

}

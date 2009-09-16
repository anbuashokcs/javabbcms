package org.javabb.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IIndexLucenePostsDAO;
import org.javabb.transaction.PostTransaction;
import org.javabb.vo.Post;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Dalton Camargo
 */
public class IndexPostsJob extends QuartzJobBean {

	protected final Log _log = LogFactory.getLog(this.getClass());

	private PostTransaction postTransaction;
	private IIndexLucenePostsDAO indexLucenePostsDAO;
	public void setIndexLucenePostsDAO(IIndexLucenePostsDAO indexLucenePostsDAO) {
		this.indexLucenePostsDAO = indexLucenePostsDAO;
	}
	public void setPostTransaction(PostTransaction postTransaction) {
		this.postTransaction = postTransaction;
	}

	private void indexPosts() throws Exception {
		indexLucenePostsDAO.updateIndexPosts();
		
		
		/*List posts = null;
		try {
			System.out.println("Loading posts..");
			posts = indexLucenePostsDAO.updateIndexPosts();
			System.out.println("Loaded..");
			for (int i = 0; i < posts.size(); i++) {
				Post p = (Post)posts.get(i);
				System.out.println("Indexing post " + p.getId());
				postTransaction.indexPost(p);
				System.out.println("Indexed");
			}
		} catch (Exception e) {
			_log.debug("Error retriving topics: " + e);
		}*/

	}

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			_log.debug("Initializing index posts..");
			indexPosts();
			_log.debug("Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

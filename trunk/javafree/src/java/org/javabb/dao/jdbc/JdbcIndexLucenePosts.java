package org.javabb.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IIndexLucenePostsDAO;
import org.javabb.transaction.PostTransaction;
import org.javabb.vo.Post;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Migration Class. This clas was created only to migrate the old JavaFree
 * database that were using the Old Lucene version, to the new lucene version,
 * now the indexes of lucene are indexing the forumIds. That's possible to
 * search by ForumId now. This class must work with IndexPostsJob Quartz. (See
 * Spring Config File for more details)
 * 
 * @author Dalton Camargo
 * 
 */
public class JdbcIndexLucenePosts extends JdbcSuper implements
		IIndexLucenePostsDAO {

	protected final Log log = LogFactory.getLog(getClass());

	private PostTransaction postTransaction;

	public void setPostTransaction(PostTransaction postTransaction) {
		this.postTransaction = postTransaction;
	}

	public List updateIndexPosts() {
		List posts = new ArrayList();
		int initSize = 0;
		int endSize = 10000;
		//int initSize = 75001;
		//int endSize =  130000;
		for (;;) {
			Statement stmt = null;
			ResultSet rs = null;
			try {

				stmt = this.getConnection().createStatement();

				// The number of posts of JavaFree today is 130.000
				if (endSize == 130001) {
					break;
				}

				System.out.println("Initializing ResultSet...");
				System.out.println("InitSize:" + initSize + "-EndSize:" + endSize);
				rs = stmt
						.executeQuery(" SELECT p.assunto, pt.post_body, p.id_post, p.id_user, p.id_topic, p.data_post, "
								+ " t.id_forum, t.title_topic "
								+ " FROM jbb_posts p, jbb_topics t, jbb_posts_text pt WHERE pt.id_post = p.id_post AND"
								+ " t.id_topic = p.id_topic "
								+ " ORDER BY p.id_topic ASC  limit "
								+ initSize
								+ ", " + endSize);

				initSize = endSize + 1;
				endSize = endSize + 5000;

				System.out.println("ResultSet Initialized..");

				while (rs.next()) {
					Long postId = new Long(rs.getLong("id_post"));
					Long userId = new Long(rs.getLong("id_user"));
					Long topicId = new Long(rs.getLong("id_topic"));
					Date postDate = rs.getDate("data_post");
					Long forumId = new Long(rs.getLong("id_forum"));
					String titleTopic = rs.getString("title_topic");
					String subject = rs.getString("assunto");
					String body = rs.getString("post_body");

					Post p = new Post(postId, userId, topicId, postDate,
							forumId, titleTopic, subject, body);

					System.out.println("Indexing post " + p.getId());

					postTransaction.indexPost(p);
					p = null;
				}

			} catch (CannotGetJdbcConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					getConnection().commit();
					rs.close();
					rs = null;
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
				}
			}
		}
		return posts;
	}
}

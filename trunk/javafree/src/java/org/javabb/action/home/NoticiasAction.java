package org.javabb.action.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.PostTransaction;
import org.javabb.vo.Comment;
import org.javabb.vo.Noticias;
import org.javabb.vo.Post;
import org.javabb.vo.User;

import com.opensymphony.webwork.ServletActionContext;

public class NoticiasAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private PostTransaction postTransaction;

	public void setPostTransaction(PostTransaction postTransaction) {
		this.postTransaction = postTransaction;
	}

	private Noticias noticia = new Noticias();
	private List<Noticias> destaques;
	private List<Noticias> noticias;
	private List<Noticias> noticiasSecond;
	private List<Noticias> relatedNoticias;
	private Comment comment;
	private List<Comment> comments;
	private List<Post> posts;

	private List<String> msgSuccess;

	public Object getModel() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public String loadHome() throws Exception {
		destaques = noticiasTransaction.findDestaquesHome(1, 5);
		noticias = noticiasTransaction.findByNoticias(1, 20);
		noticiasSecond = noticiasTransaction.findByNoticias(2, 20);
		posts = postTransaction.findLastShortPosts(12);
		comments = noticiasTransaction.findLastComments(10);
		return SUCCESS;
	}

	public String rssNoticias() throws Exception {
		noticias = noticiasTransaction.findByNoticias(1, 20);
		return SUCCESS;
	}

	public String noticiasPendentes() throws Exception {
		noticias = noticiasTransaction.findPendentes(getPage());
		return SUCCESS;
	}

	public String loadNoticia() throws Exception {
		noticia = noticiasTransaction.loadNoticia(noticia.getNotId(), true);
		// relatedNoticias =
		// noticiasTransaction.searchRelatedNoticias(noticia.getTitle(),
		// noticia.getNotId());
		noticias = noticiasTransaction.findByNoticias(1, 11);
		comments = noticiasTransaction.findCommentByNotId(noticia.getNotId());
		/*
		 * System.out.println(ServletActionContext.getRequest().getContextPath()
		 * + "/" + ServletActionContext.getContext().getName() + "/" +
		 * ServletActionContext.getRequest().getQueryString());
		 */
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private boolean checkAddNoticia() {
		msgErrors = new ArrayList();

		if ("".equals(noticia.getTitle())) {
			msgErrors.add("Você precisa informar um titulo.");
			return false;
		}
		if ("".equals(noticia.getLink())) {
			msgErrors.add("Você precisa informar um link.");
			return false;
		}
		if ("".equals(noticia.getBody())) {
			msgErrors.add("Você precisa informar uma mensagem.");
			return false;
		}

		/*
		if (!checkCaptcha()) {
			msgErrors.add("Codigo digitado não confere com a imagem.");
			return false;
		}
		*/

		return true;
	}

	@SuppressWarnings("unchecked")
	public String addNoticia() throws Exception {
		// Não passou na validação
		if (!checkAddNoticia()) {
			return INPUT;
		}

		if (checkUserLogged()) {
			User user = getUserLogged();
			noticia.setUser(user);
			noticia.setActive(0);
			if (user.getAdmin() != 1) {
				noticia.setHome(0);
			}
			if (noticia.getHome() == null) {
				noticia.setHome(0);
			}
			noticiasTransaction.addNoticia(noticia);

			msgSuccess = new ArrayList();
			msgSuccess
					.add("Obrigado! Sua notícia foi enviada e em breve será moderada.");
			noticia = new Noticias();
		}

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String addComentario() throws Exception {

		String retFlood = checkFlood();
		if (StringUtils.isNotEmpty(retFlood)) {
			return retFlood;
		}

		if ("".equals(comment.getBody()) && "".equals(comment.getBody().trim())) {
			msgErrors = new ArrayList();
			msgErrors.add("O comentário precisa ser preenchido.");
			loadNoticia();
			return INPUT;
		}

		comment.setUser(getUserLogged());
		comment.setNoticias(noticia);

		msgSuccess = new ArrayList();
		msgSuccess.add("Obrigado! Seu comentário foi publicado!");

		Long commentId = noticiasTransaction.addComment(comment);

		// http://localhost:8080/javafree/noticia/5/JF+muda+de+layoutsdfsd
		String basePath = ServletActionContext.getRequest().getContextPath();

		setUrl(basePath + "/noticia/" + getNoticia().getNotId() + "/#"
				+ commentId);

		return SUCCESS;
	}

	public String excluirNoticia() throws Exception {
		if (noticia.getNotId() != null) {
			noticiasTransaction.excluirNoticia(noticia.getNotId());
		}
		return SUCCESS;
	}

	public String excluirComment() throws Exception {
		if (comment.getId() != null) {
			noticiasTransaction.deleteComment(comment);
			loadNoticia();
		}
		return SUCCESS;
	}

	public String loadEditNoticia() throws Exception {
		noticia = noticiasTransaction.loadNoticia(noticia.getNotId(), false);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updateEditNoticia() throws Exception {
		noticiasTransaction.updateNoticia(noticia.getNotId(), noticia);

		msgSuccess = new ArrayList();
		msgSuccess.add("Notícia alterada!");
		noticia = new Noticias();

		return SUCCESS;
	}

	public String approveNoticia() throws Exception {
		noticiasTransaction.approveNoticia(noticia.getNotId());
		return SUCCESS;
	}

	public String reproveNoticia() throws Exception {
		noticiasTransaction.reproveNoticia(noticia.getNotId());
		return SUCCESS;
	}

	public String listAllNoticias() throws Exception {
		noticias = noticiasTransaction.findAllNoticias(getPage());
		return SUCCESS;
	}

	public String indexLuceneNoticias() throws Exception {
		noticiasTransaction.luceneIndexNoticias();
		return SUCCESS;
	}

	public List<Noticias> getDestaques() {
		return destaques;
	}

	public void setDestaques(List<Noticias> destaques) {
		this.destaques = destaques;
	}

	public Noticias getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticias noticia) {
		this.noticia = noticia;
	}

	public List<String> getMsgSuccess() {
		return msgSuccess;
	}

	public void setMsgSuccess(List<String> msgSuccess) {
		this.msgSuccess = msgSuccess;
	}

	public List<Noticias> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticias> noticias) {
		this.noticias = noticias;
	}

	public List<Noticias> getRelatedNoticias() {
		return relatedNoticias;
	}

	public void setRelatedNoticias(List<Noticias> relatedNoticias) {
		this.relatedNoticias = relatedNoticias;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Noticias> getNoticiasSecond() {
		return noticiasSecond;
	}

	public void setNoticiasSecond(List<Noticias> noticiasSecond) {
		this.noticiasSecond = noticiasSecond;
	}

}

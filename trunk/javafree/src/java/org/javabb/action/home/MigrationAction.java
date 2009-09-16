package org.javabb.action.home;

import org.javabb.action.infra.BaseAction;
import org.javabb.infra.Constants;

import com.opensymphony.webwork.ServletActionContext;

@SuppressWarnings("serial")
public class MigrationAction extends BaseAction {

	private Long idContent;
	private Long idNew;

	public String redirectMigratedNews() {
		String basePath = ServletActionContext.getRequest().getContextPath();

		if (idNew != null && idNew.intValue() > 0) {
			setUrl(basePath + "/noticia/" + idNew + "/");
		} else {
			setUrl(basePath + "/index.jf");
		}

		return SUCCESS;
	}

	public String redirectMigratedArticles() {
		String basePath = ServletActionContext.getRequest().getContextPath();
		Integer migratedArti = 0;

		try {
			migratedArti = Constants.getIdNewArticleMigrated(idContent);
		} catch (Exception e) {
			log.debug("Erro ao obter codigo do artigo");
		}

		if (migratedArti.intValue() > 0) {
			setUrl(basePath + "/artigo/" + migratedArti + "/");
		} else {
			setUrl(basePath + "/index.jf");
		}

		return SUCCESS;
	}

	public String getJavaBBRequests() {
		System.out.println(getUrl());
		String basePath = ServletActionContext.getRequest().getContextPath();
		setUrl(basePath + "/" + getUrl());
		System.out.println(getUrl());
		return SUCCESS;
	}

	public Long getIdContent() {
		return idContent;
	}

	public void setIdContent(Long idContent) {
		this.idContent = idContent;
	}

	public Long getIdNew() {
		return idNew;
	}

	public void setIdNew(Long idNew) {
		this.idNew = idNew;
	}

}

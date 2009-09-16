package org.javabb.action.home;

import java.util.List;

import org.javabb.action.infra.BaseAction;
import org.javabb.transaction.WikiTransaction;
import org.javabb.vo.Wiki;

@SuppressWarnings("serial")
public class WikiAction extends BaseAction {

    private List list;
    private String word;
    private Wiki wiki;

    private WikiTransaction wikiTransaction;
    public void setWikiTransaction(WikiTransaction wikiTransaction) {
        this.wikiTransaction = wikiTransaction;
    }

    public String loadWord() throws Exception {
	if(word != null) {
	    wiki = wikiTransaction.loadWiki(word);
	    list = wikiTransaction.relatedWikiWords(word);
	}
	return SUCCESS;
    }
    
    public String loadWikiById() throws Exception {
	wiki = wikiTransaction.load(wiki);
	return SUCCESS;
    }
    
    public String saveWikiWord() throws Exception {
	wikiTransaction.update(wiki);
	return SUCCESS;
    }
    
    public String luceneReloadWiki() throws Exception{
	wikiTransaction.reloadLuceneWiki();
	return SUCCESS;
    }
    
    public String allWikies() throws Exception {
	list = wikiTransaction.loadAllWikies();
	return SUCCESS;
    }
    
    public List getList() {
        return list;
    }


    public void setList(List list) {
        this.list = list;
    }


    public String getWord() {
        return word;
    }


    public void setWord(String word) {
        this.word = word;
    }


    public Wiki getWiki() {
        return wiki;
    }


    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }


}

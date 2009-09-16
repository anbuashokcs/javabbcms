package org.javabb.transaction;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.javabb.dao.entity.IBadWordDAO;
import org.javabb.vo.BadWord;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: BadWordTransaction.java,v 1.1 2009/05/11 20:27:03 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */   


public class BadWordTransaction {
    /**
     * Sobrepõe o atributo dao definido na superclasse Este atributo está
     * sobreposto para poder adquirir os métodos específicos desta entidade
     */
    private IBadWordDAO _badWordDAO = null;

    /**
     * @param dao
     */
    public void setBadWordDAO(IBadWordDAO dao) {
        this._badWordDAO = dao;
    }

    public static List badwords;

    /**
     * @return list
     */
    public List listAll() {
        try {
            if (badwords == null) {
                badwords = _badWordDAO.findAll();
            }
            return badwords;
        } catch (Exception e) {
            throw new RuntimeException(e); // XXX change to another exception
        }
    }

    /**
     * @param post
     * @return verified post
     */
    public String verifyBadWords(String post) {
    	
    	//apenas para nao comentar o metodo
    	if(1==1){
    		return post;
    	}
    	
        // Verificação de badwords no post
        if (badwords == null) {
            badwords = listAll();
        }

        for (Iterator it = badwords.iterator(); it.hasNext();) {
            BadWord bw = (BadWord) it.next();

            try {
                String badword = bw.getWord();
                String reBadWord = "(?:^|\\b)" + badword + "s?(?:^|\\b)";
                Pattern pattern = Pattern.compile(reBadWord,
                        Pattern.CASE_INSENSITIVE);
                char firstLetter;

                if ((badword != null) && (badword.length() > 0)
                        && Character.isLetterOrDigit(badword.charAt(0))) {
                    firstLetter = badword.charAt(0);
                } else {
                    firstLetter = '*';
                }

                try {
                    post = pattern.matcher(post).replaceAll(
                            firstLetter + bw.getReplacement());
                } catch (Exception e) {
                    post = pattern.matcher(post)
                            .replaceAll(firstLetter + "***");
                }
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }
        }

        return post;
    }

    /**
     * @param id
     * @return badword
     */
    public BadWord getBadWord(Long id) {
        return _badWordDAO.load(id);
    }

    /**
     * @param badword
     */
    
    public void delete(BadWord badword) {
        _badWordDAO.delete(badword);
        badwords = null;
    }

    /**
     * @param badword
     */
    
    public void update(BadWord badword) {
        BadWord bdToUpdate = this.getBadWord(badword.getIdBadWord());
        bdToUpdate.setReplacement(badword.getReplacement());
        bdToUpdate.setWord(badword.getWord());
        badwords = null;
    }

    /**
     * @param badword
     */
    
    public void save(BadWord badword) {
        _badWordDAO.save(badword);
        badwords = null;
    }
}
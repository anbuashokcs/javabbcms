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

package org.javabb.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.bbcode.ProcessBBCode;
import org.javabb.infra.Utils;
import org.javabb.transaction.BadWordTransaction;
import org.javabb.transaction.SmileTransaction;
import org.javabb.vo.Post;
import org.springframework.web.util.HtmlUtils;

/**
 * $Id: PostFormatter.java,v 1.1 2009/05/11 20:27:08 daltoncamargo Exp $
 * @author Ronald Tetsuo Miura
 */
public class PostFormatter {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
    /** */
    private BadWordTransaction _badWordTransaction;

    /** */
    private SmileTransaction _smileTransaction;

    /**
     * @param badWordTransaction
     */
    public void setBadWordTransaction(BadWordTransaction badWordTransaction) {
        this._badWordTransaction = badWordTransaction;
    }

    /**
     * @param smileTransaction
     */
    public void setSmileTransaction(SmileTransaction smileTransaction) {
        _smileTransaction = smileTransaction;
    }

    /**
     * @param post
     * @return bbcode-formated text
     */
    public String formatPost(String basePath, Post post) {
    	ProcessBBCode bbcodeFormatter = new ProcessBBCode();
        bbcodeFormatter.setAcceptHTML(post.getAcceptHTML());
        bbcodeFormatter.setAcceptBBCode(post.getAcceptBBCode());
        
        bbcodeFormatter.setAcceptHTML(false);
        bbcodeFormatter.setAcceptBBCode(true);

        String text = Utils.verifyURLs(post.getPostBody());
        text = bbcodeFormatter.preparePostText(text);
        text = _badWordTransaction.verifyBadWords(text);
        text = _smileTransaction.replaceSmiles(basePath, text);

        return text;
    }

    
    public String formatWithoutBBCode(String basePath, Post post) {
        String text = Utils.verifyURLs(post.getPostBody());
        text = HtmlUtils.htmlEscape(text);
        text = _badWordTransaction.verifyBadWords(text);
        text = _smileTransaction.replaceSmiles(basePath, text);
        return text;
    }
    
    
    public String formatTextToBBCode(String basePath, String textToBBcode){
    	
        ProcessBBCode bbcodeFormatter = new ProcessBBCode();
        bbcodeFormatter.setAcceptHTML(false);
        bbcodeFormatter.setAcceptBBCode(true);

        String text = Utils.verifyURLs(textToBBcode);
        text = bbcodeFormatter.preparePostText(text);
        text = _badWordTransaction.verifyBadWords(text);
        text = _smileTransaction.replaceSmiles(basePath, text);
        return text;
    	
    }
    
    /**
     * @param text
     * @return bbcode-formated text
     */
    public String formatEscaped(String text) {
        ProcessBBCode bbcodeFormatter = new ProcessBBCode();
        bbcodeFormatter.setAcceptHTML(false);
        bbcodeFormatter.setAcceptBBCode(false);
        text = bbcodeFormatter.preparePostText(text);
        text = _badWordTransaction.verifyBadWords(text);
        return text;
    }
    
    public String formatOnlyBBCodeHTML(String text) {
	ProcessBBCode bbcodeFormatter = new ProcessBBCode();
        bbcodeFormatter.setAcceptHTML(true);
        bbcodeFormatter.setAcceptBBCode(true);
        text = bbcodeFormatter.preparePostText(text);
        return text;
    }
}

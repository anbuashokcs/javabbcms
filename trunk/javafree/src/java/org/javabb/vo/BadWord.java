package org.javabb.vo;

import java.io.Serializable;

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

/* $Id: BadWord.java,v 1.1 2009/05/11 20:26:51 daltoncamargo Exp $ */
/**
 * This class represent a offensive word that should be censured in forum.
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class BadWord extends VOObject implements Serializable {

    private String word;

    private String replacement;

    /**
     * Default constructor.
     */
    public BadWord() {
        // do nothing
    }

    /**
     * @param id
     */
    public BadWord(Long id) {
        setId(id);
    }

    /**
     * @param word
     * @param replacement
     */
    public BadWord(String word, String replacement) {
        this(null, word, replacement);
    }

    /**
     * @param id
     * @param word
     * @param replacement
     */
    public BadWord(Long id, String word, String replacement) {
        setId(id);
        setWord(word);
        setReplacement(replacement);
    }

    /**
     * @return Returns the idBadWord.
     */
    public Long getIdBadWord() {
        return getId();
    }

    /**
     * @param id The idBadWord to set.
     */
    public void setIdBadWord(Long id) {
        this.setId(id);
    }

    /**
     * @return Returns the replacement.
     */
    public String getReplacement() {
        return replacement;
    }

    /**
     * @param replacement The replacement to set.
     */
    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    /**
     * @return Returns the word.
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word The word to set.
     */
    public void setWord(String word) {
        this.word = word;
    }
}

package org.javabb.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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

/**
 * $Id: Smile.java,v 1.1 2009/05/11 20:26:49 daltoncamargo Exp $
 * @author Ronald Tetsuo Miura
 */
public class Smile implements Serializable {

    /** */
    private Long _id;
    /** */
    private String _emoticon;
    /** */
    private String _symbol;
    /** */
    private String _filename;

    /**
     * @param id
     * @param emoticon
     * @param symbol
     * @param filename
     */
    public Smile(Long id, String emoticon, String symbol, String filename) {
        this._id = id;
        this._symbol = symbol;
        this._emoticon = emoticon;
        this._symbol = symbol;
        this._filename = filename;
    }

    /**
     * @param emoticon
     * @param symbol
     * @param filename
     */
    public Smile(String emoticon, String symbol, String filename) {
        this(null, emoticon, symbol, filename);
    }

    /**
     * Default constructor.
     */
    public Smile() {
        // do nothing
    }

    /**
     * @param id
     */
    public Smile(Long id) {
        setId(id);
    }

    /**
     * @return id
     */
    public Long getId() {
        return _id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this._id = id;
    }

    /**
     * @return emotion
     */
    public String getEmoticon() {
        return _emoticon;
    }

    /**
     * @param emotion
     */
    public void setEmoticon(String emotion) {
        this._emoticon = emotion;
    }

    /**
     * @return textual symbol of the emoticon
     */
    public String getSymbol() {
        return this._symbol;
    }

    /**
     * @param symbol
     */
    public void setSymbol(String symbol) {
        this._symbol = symbol;
    }

    /**
     * @return the image filename
     */
    public String getFilename() {
        return _filename;
    }

    /**
     * @param imageFile the new image filename.
     */
    public void setFilename(String imageFile) {
        this._filename = imageFile;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append(getId())
            .append(getSymbol())
            .append(getEmoticon())
            .append(getFilename())
            .toString();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (!(o instanceof Smile)) {
            return false;
        }
        Smile e = (Smile) o;
        return new EqualsBuilder().append(this.getId(), e.getId()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
}

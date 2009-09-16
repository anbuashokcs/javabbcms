package org.javabb.vo;

import java.io.Serializable;
import java.util.List;

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
 * $Id: Category.java,v 1.1 2009/05/11 20:26:51 daltoncamargo Exp $
 */
/**
 * This class define a category of forums
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Category extends VOObject implements Serializable {

    private Long idCategory;

    /** not nullable persistent field */
    private String nameCategory;

    /** not nullable persistent field */
    private Integer catOrder;

    /** persistent field * */
    private List foruns;

    /**
     * @return Returns the idCategory.
     */
    public Long getIdCategory() {
	return getId();
    }

    /**
     * @param id
     *            The idCategory to set.
     */
    public void setIdCategory(Long id) {
	setId(id);
	idCategory = id;
    }

    /**
     * @return Returns the nameCategory.
     */
    public String getNameCategory() {
	return nameCategory;
    }

    /**
     * @param nameCategory
     *            The nameCategory to set.
     */
    public void setNameCategory(String nameCategory) {
	this.nameCategory = nameCategory;
    }

    /**
     * @return Returns the foruns.
     */
    public List getForuns() {
	return foruns;
    }

    /**
     * @param foruns
     *            The foruns to set.
     */
    public void setForuns(List foruns) {
	this.foruns = foruns;
    }

    /**
     * @return Returns the catOrder.
     */
    public Integer getCatOrder() {
	return catOrder;
    }

    /**
     * @param catOrder
     *            The catOrder to set.
     */
    public void setCatOrder(Integer catOrder) {
	this.catOrder = catOrder;
    }
}

package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vh.Stats;
import org.javabb.vo.Category;

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
 * $Id: ICategoryDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface ICategoryDAO extends DAOConstants {

    /** */
    public static final int ID = 1;
    /** */
    public static final int SORTING_POSITION = 2;
    /** */
    public static final int NAME = 3;

    /**
     * @return post count
     */
    public int countAllPosts();

    /**
     * @return topic count
     */
    public int countAllTopics();

    /**
     * @return user count
     */
    public int countAllUsers();

    /**
     * @return
     */
    public Stats getStatistics();

    /**
     * @param categoryId
     * @return category
     */
    public Category load(Long id, boolean fromCache);

    /**
     * @param orderingFields
     * @return
     */
    public List findAllCategoriesBySortingPosition();
    public List findAllCategoriesByName();
    
    public Category insertCategory(Category cat);
    public Category getLastCattegoryByOrder();
    public void deleteCategory(Category cat);
    
}

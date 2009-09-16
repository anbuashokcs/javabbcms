package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Smile;

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
 * $Id: ISmileDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface ISmileDAO extends DAOConstants {
    /**
     * @return a List of all Emoticons
     */
    public List findAll();

    /**
     * @param id
     * @return emoticon
     */
    public Smile load(Long id);

    /**
     * @param smile
     * @return new id
     */
    public Long create(Smile smile);

    /**
     * @param emoticonId
     */
    public void delete(Long emoticonId);

    /**
     * @param s
     */
    public void update(Smile s);
}

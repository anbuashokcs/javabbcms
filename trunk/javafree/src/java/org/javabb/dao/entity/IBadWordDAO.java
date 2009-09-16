package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
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

/* $Id: IBadWordDAO.java,v 1.1 2009/05/11 20:26:56 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public interface IBadWordDAO extends DAOConstants {

    /**
     * @return badwords
     */
    public List findAll();

    /**
     * @param id
     * @return badword
     */
    public BadWord load(Long id);

    /**
     * @param badword
     */
    public void delete(BadWord badword);


    /**
     * @param badword
     */
    public void save(BadWord badword);
}

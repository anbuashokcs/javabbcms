package org.javabb.dao.entity;

import java.util.List;
import java.util.Set;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Wiki;

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
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public interface IWikiDAO extends DAOConstants {

    public void update( Wiki wiki ) throws Exception;
    public Wiki loadWikiWord(String word) throws Exception;
    public void reloadLuceneWiki() throws Exception;
    public Set searchRelatedWiki(String query) throws Exception;
    public List loadAllWikies() throws Exception;
   
}

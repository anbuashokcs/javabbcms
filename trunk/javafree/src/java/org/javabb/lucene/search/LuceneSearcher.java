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
package org.javabb.lucene.search;

import java.util.List;

/**
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 * 
 * @version $Id: LuceneSearcher.java,v 1.1 2009/05/11 20:27:10 daltoncamargo Exp $
 */
public interface LuceneSearcher {
    /**
     * @param query
     * @param field
     * @return
     */
    List search(String query[], String field[]);

    List search(String query, String field[], String fieldKey);

    List searchSimilarWords(String query, String[] fields, String fieldKey);
}

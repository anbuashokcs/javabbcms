package org.javabb.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork.ActionContext;

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
 * $Id: Paging.java,v 1.1 2009/05/11 20:27:02 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public class Paging {
    /**
     * @param recordsPerPage - Registros por página
     * @param nroRecords - Número total de registros
     * @return Retorna o número de páginas
     */
    public static int getNroPages(long recordsPerPage, long nroRecords) {
        double pages = Math.ceil((double) nroRecords / recordsPerPage);

        return (int) Math.round(pages);
    }

    /**
     * @param pageNumber
     * @param totalRows
     */
    public static void setPageList(long pageNumber, long totalRows) {
        ArrayList pages = new ArrayList();

        if ((pageNumber - 3) > 0) {
            for (long i = (pageNumber - 3); i < pageNumber; i++) {
                pages.add("" + i);
            }
        } else {
            for (long i = 1; i < pageNumber; i++) {
                pages.add("" + i);
            }
        }

        if ((pageNumber + 3) <= totalRows) {
            for (long i = pageNumber; i <= (pageNumber + 3); i++) {
                pages.add("" + i);
            }
        } else {
            for (long i = pageNumber; i <= totalRows; i++) {
                pages.add("" + i);
            }
        }

        // Insere o conteúdo das páginas na sessão
        ActionContext ctx = ActionContext.getContext();
        Map session = ctx.getSession();
        session.put("pages", pages);

        // Insere a última página na sessão
        session.put("last_page", new Integer((int) totalRows));
    }

    /**
     * Create a list of six pages, when the number of pages will be greater that ten
     * @param nroPages
     * @return list
     */
    public static List createQuickPaging(int nroPages) {
        ArrayList pages = new ArrayList();
        if (nroPages >= 10) {
            pages.add(new Integer(1));
            pages.add(new Integer(2));
            pages.add(new Integer(3));
            pages.add("...");
            pages.add(new Integer(nroPages - 2));
            pages.add(new Integer(nroPages - 1));
            pages.add(new Integer(nroPages));
        } else {
            for (int i = 1; i <= nroPages; i++) {
                pages.add(new Integer(i));
            }
        }
        return pages;
    }
}

package org.javabb.infra;

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
 * $Id: Parser.java,v 1.1 2009/05/11 20:27:02 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 */
public class Parser {
    /**
     * um HQL é passado e nele é substituido {vo} pelo caminho do VO na aplicação
     * @param hql a ser parseado
     * @return HQL parseado
     */
    public static String replaceHQL(String hql) {
        return hql.replaceAll("\\{vo\\}", "org.javabb.vo.");
    }

    /**
     * Cofirm if a parameter are an integer
     * @param param
     * @return true for yes, and false to not
     */
    public static boolean isInt(String param) {
        try {
            new Integer(param);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return lang of locale field
     * @param locale - ex: en_US
     * @return en
     */
    public static String getLang(String locale) {
        if (locale == null) {
            // Case the param is null, return default configuration
            return "en";
        }

        String[] lc = locale.split("_");

        return lc[0];
    }

    /**
     * Return Country of locale field
     * @param locale - ex: en_US
     * @return US
     */
    public static String getCountry(String locale) {
        if (locale == null) {
            // Case the param is null, return default configuration
            return "US";
        }

        String[] lc = locale.split("_");

        return lc[1];
    }
}

package org.javabb.infra;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * $Id: Utils.java,v 1.1 2009/05/11 20:27:00 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Utils {
    private static final Log LOG = LogFactory.getLog(Utils.class);

    private static final Random RANDOM = new Random();

    /**
     * Encodes a string
     * @param str
     * @return md5
     */
    public static String encrypt(String str) {
        String sign = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte[] hash = md.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }

            sign = hexString.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return sign;
    }

    /**
     * Obtém um número randômico
     * @return número randomizado
     */
    public static String randomNumber() {
        if (System.currentTimeMillis() % 10000 == 0) {
            RANDOM.setSeed(System.currentTimeMillis());
        }
        return String.valueOf(RANDOM.nextLong());
    }

    /**
     * Valida o WebSite digitado, e insere por padrão o protocolo http em frente.
     * @param ws endereço para padronizar
     * @return endereço padronizado
     */
    public static String validateWebSite(String ws) {
        if ((ws != null) && (ws.length() > 0)) {
            ws = ws.replaceAll("http://", "");
            ws = "http://" + ws;

            return ws;
        }
        return "";
    }

    /**
     * @param text
     * @return verified text
     */
    public static String verifyURLs(String text) {
    	if(text == null){
    		return text;
    	}
        final String regex = "([\\s\\n\\r\\t\\.,]|^)((?:http://|https://|ftp://)(?:\\S*))";
        final String replacement = "$1[url]$2[/url]";

        // Verificação de URLs no post
        try {
            text = text.replaceAll(regex, replacement);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
        }

        final String wwwregex = "([\\s\\n\\r\\t\\.,]|^)(www\\.\\S*)";
        final String wwwreplacement = "$1[url=\"http://$2\"]$2[/url]";

        // Verificação de URLs no post
        try {
            text = text.replaceAll(wwwregex, wwwreplacement);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
        }

        final String emailregex = "([\\s\\n\\r\\t\\.,]|^)([\\w-]+(?:\\.[\\w-]+)*@[\\w-]+\\.[\\w-]+(?:\\.[\\w-]+)*)";
        final String emailreplacement = "$1[url=\"mailto:$2\"]$2[/url]";

        // Verificação de emails no post
        try {
            text = text.replaceAll(emailregex, emailreplacement);
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
        }

        return text;
    }

    /**
     * @param texto
     * @return text
     */
    public static String replaceHTML(String texto) {
    	if(texto == null){
    		return "";
    	}
        return texto.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    /**
     * @param userName
     * @return code
     */
    public static String getCodeUser(String userName) {
        String codeUser = userName + System.currentTimeMillis() + Utils.randomNumber();
        return Utils.encrypt(codeUser);
    }

	/**
	 * @param string
	 * @return
	 */
	public static String avoidNull(String string) {

		return string != null ? string : "";
	}

	/**
	 * @author Dalton Camargo
	 * Get all String positions, similiar indexOf
	 * @param text - Text to be parsed
	 * @param key - Key to be found
	 * @return List<Integer> with all positions
	 */    
    public static List indexOf(String text, String key){
    	if(text == null || key == null){
    		return null;
    	}
    	List lst = new ArrayList();
    	int i = 0;
    	while(true){
    		int occur = text.indexOf(key, ((i>text.length())?text.length():i));
    		if(occur == -1){
    			break;
    		} else {
    			lst.add(new Integer(occur));
    			i= occur + 1;
    		}
    	}
    	return lst;
    }
    
    
    public static boolean isBetween(int index, List initCodePos, List finalCodePos){
    	for(int y=0; y<initCodePos.size(); y++){
			int init = ((Integer) initCodePos.get(y)).intValue();
			int limit = ((Integer) finalCodePos.get(y)).intValue();
			LOG.info("init:" + init + " limit:" + limit+ " index:" + index);
			if(index > init && index < limit){
				return true;
			}
    	}
    	return false;
    }

	public static boolean validateEmail(String email) {
		Pattern padrao = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher pesquisa = padrao.matcher(email);
		if (pesquisa.matches()) {
			return true;
		} else {
			return false;
		}
	}
    
	/**
	 * Composite Word
	 * @param str
	 * @return
	 */
	public static boolean compositeWord(String str){
		if(str == null){
			return false;
		}
		String[] words = str.split(" ");
		for(int i=0; i<words.length; i++){
			if(words[i].length() == 2){
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args){
	    //System.out.println(Utils.encrypt("dalton"));
		System.out.println(Utils.indexOf("dalt:)on ca:)m..:)", ":)"));
		
	}
}

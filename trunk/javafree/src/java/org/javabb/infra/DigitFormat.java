package org.javabb.infra;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
 * $Id: DigitFormat.java,v 1.1 2009/05/11 20:27:01 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org</a>
 */
public class DigitFormat {

	protected static Log log = LogFactory.getLog(DigitFormat.class);
	
	private static DecimalFormat currencyFormat() {
		DecimalFormat dec = new DecimalFormat(
				"#,###");
		DecimalFormatSymbols decDef = new DecimalFormatSymbols();
		decDef.setZeroDigit('0');
		decDef.setDecimalSeparator(',');
		decDef.setMonetaryDecimalSeparator(',');
		decDef.setDigit('#');
		decDef.setGroupingSeparator('.');
		dec.setDecimalFormatSymbols(decDef);
		return dec;
	}

	/**
	 * Faz o parser de um valor STRING
	 * @param valor
	 * @return
	 */
	public static String parserValue(String paramValue) {
		String valor = currencyFormat().format(new Double(paramValue));	
    	String tmpVlr = valor;
    	if(valor.length() > 4){
    		tmpVlr = valor.substring(valor.length() -4, valor.length());
    		if(tmpVlr.equals("0000")){
    			tmpVlr = valor.substring(0, valor.length()-4);
    		} else {
    			tmpVlr = valor;
    		}
    	}    	
    	return tmpVlr;
	}
	
	public static void main(String[] args){
		System.out.println(DigitFormat.parserValue("1.5435555E7"));
	}

}
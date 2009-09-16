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

package org.javabb.transaction;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.javabb.dao.entity.ISmileDAO;
import org.javabb.infra.Utils;
import org.javabb.vo.Smile;

/**
 * @author
 * @since 27/01/2005
 */
public class SmileTransaction {
	private ISmileDAO _smileDAO;

	private static List _smileCache;

	// ####################################################################
	// Dependencies
	// ####################################################################

	/**
	 * @param smileDAO
	 */
	public void setSmileDAO(ISmileDAO smileDAO) {
		this._smileDAO = smileDAO;
	}

	// ####################################################################
	// Métodos de negócio
	// ####################################################################

	/**
	 * @param symbol
	 * @param emotion
	 * @param filename
	 */
	public void addSmile(String emotion, String symbol, String filename) {
		this._smileDAO.create(new Smile(emotion, symbol, filename));
	}

	/**
	 * @return list
	 */
	public List listAll() {

		if (_smileCache == null) {
			_smileCache = _smileDAO.findAll();
		}

		return _smileCache;
	}

	/**
	 * @param id
	 * @return smile
	 */
	public Smile getSmile(Long id) {
		return this._smileDAO.load(id);
	}

	/**
	 * @param emoticonId
	 */
	public void delete(Long emoticonId) {
		this._smileDAO.delete(emoticonId);
	}

	/**
	 * @param emoticonId
	 * @param emotion
	 * @param symbol
	 * @param filename
	 */
	public void updateSmile(Long emoticonId, String emotion, String symbol,
			String filename) {
		Smile s = this._smileDAO.load(emoticonId);
		s.setEmoticon(emotion);
		s.setSymbol(symbol);
		s.setFilename(filename);
	}

	/**
	 * @param text
	 * @return text
	 */
	public String replaceSmiles(String basePath, String text) {

		List smiles = listAll();

		StringBuffer sb = new StringBuffer(text);

		try {

			// Check by [code] tag
			String match = "[ -code- ]";
			String finalMatch = "[/ -code- ]";

			List initCodePos = Utils.indexOf(text, match);
			List finalCodePos = Utils.indexOf(text, finalMatch);
			boolean hasCode = (initCodePos != null && !initCodePos.isEmpty()) ? true
					: false;

			for (Iterator it = smiles.iterator(); it.hasNext();) {
				Smile smile = (Smile) it.next();
				String symbol = smile.getSymbol();
				if (symbol.length() == 0) {
					break;
				}

				// TODO purge hard-coded path
				String imgSrc = "<img src='" + basePath
						+ "/forum/images/smiles/" + smile.getFilename() + "'>";

				String origin = new String(text);
				int limit = 0;
				if (hasCode) {
					/*
					 * String firstPart = ""; String lastPart = "";
					 * 
					 * for (int i = 0; i < initCodePos.size(); i++) { int init =
					 * ((Integer) initCodePos.get(i)).intValue(); limit =
					 * ((Integer) finalCodePos.get(i)).intValue();
					 * 
					 * String middlePart = origin.substring(init, limit);
					 * 
					 * firstPart = ""; lastPart = ""; if (i == 0) { firstPart =
					 * origin.substring(0, init); lastPart =
					 * origin.substring(limit, origin.length()); } else{ int
					 * oldLimit = ((Integer) finalCodePos.get(i-1)).intValue();
					 * firstPart = origin.substring(oldLimit, init); lastPart =
					 * origin.substring(limit, origin.length()); } firstPart =
					 * StringUtils.replace(firstPart, symbol, imgSrc); origin =
					 * StringUtils.join(new String[] { firstPart, middlePart,
					 * lastPart }); }
					 * 
					 * firstPart = origin.substring(0, limit); lastPart =
					 * origin.substring(limit, origin.length()); origin =
					 * StringUtils.join(new String[] { firstPart, lastPart });
					 */

				} else {
					origin = StringUtils.replace(origin, symbol, imgSrc);
				}

				// List smilePos = Utils.indexOf(text, symbol);
				// for(int i=0; i<smilePos.size(); i++){
				// int sIndex = ((Integer) smilePos.get(i)).intValue();
				// if(hasCode){
				// if(Utils.isBetween(sIndex, initCodePos, finalCodePos)){
				// System.out.println("BBCODE tag is found on Smiles replace");
				// } else{
				// sb = sb.replace(sIndex, sIndex + symbol.length(), imgSrc);
				// }
				// } else{
				// sb = sb.replace(sIndex, sIndex + symbol.length(), imgSrc);
				// }
				// }

				// List smilePos = Utils.indexOf(text, symbol);
				// int index = text.lastIndexOf(symbol);
				// if (hasCode) {
				// while (index >= 0) {
				// for (int i =0 ; i < initCodePos.size(); i++) {
				// int init = ((Integer) initCodePos.get(i)).intValue();
				// int limit = ((Integer) finalCodePos.get(i)).intValue();
				//							
				// if ((index <= init && index >= limit) ||
				// (index >= init && index <= limit)) {
				// System.out.println("BBCODE tag is found on Smiles replace");
				// System.out.println("BBCODE init: " + init + " limit: " +
				// limit + " index: " + index);
				// } else {
				// //if there is no bbcode tag, make replace
				// System.out.println("smilePos: " + smilePos +" init: " + init
				// + " limit: " + limit + " index: " + index);
				// sb.replace(index, index + symbol.length(), imgSrc);
				//
				// int nextIndex = index - symbol.length();
				// if (nextIndex < 0) {
				// break;
				// }
				// index = text.lastIndexOf(symbol, nextIndex);
				// }
				// }
				//
				//						
				// }
				// } else {
				//	
				// // if there is no bbcode tag, make replace
				// while (index >= 0) {
				// sb.replace(index, index + symbol.length(), imgSrc);
				// int nextIndex = index - symbol.length();
				// if (nextIndex < 0) {
				// break;
				// }
				// index = text.lastIndexOf(symbol, nextIndex);
				// }
				// }

				// text = text.replaceAll(" \\" + symbol + " ", imgSrc);

				text = origin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
}

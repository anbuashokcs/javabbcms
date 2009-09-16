package org.javabb.infra;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.bbcode.ProcessBBCode;
import org.springframework.web.util.HtmlUtils;

/**
 * @author Dalton Camargo - <a href="mailto:dalton@ag2.com.br">dalton@ag2.com.br
 *         </a> <Br>
 *         AG2 - Agencia de Inteligencia Digital S.A. <Br>
 *         <a href="http://www.ag2.com.br">http://www.ag2.com.br </a> <Br>
 *         Nosso <i>www </i> e mais <b>inteligente </b>!
 */

/* $Id: VelocityHelper.java,v 1.2 2009/06/28 23:52:07 daltoncamargo Exp $ */
public class VelocityHelper {

	protected Log log = LogFactory.getLog(VelocityHelper.class);

	public String printObj(Object obj) {
		return ("Objeto = " + obj);
	}

	/**
	 * Gerencia se um parâmetro no velocity é nullo ou não Exemplo:
	 * 
	 * #if($vl.isNull($evento.getEventoId()) == 1) Parâmetro nullo #end
	 * 
	 * @param obj
	 * @return
	 */
	public int isNull(Object obj) {
		if (obj == null) {
			return 1;
		} else {
			return 0;
		}
	}

	public String dateDiff(Date date) {
		try {
			Calendar cal = GregorianCalendar.getInstance();
			long days = DateUtil.getDiffDates(cal.getTime(), date);
			days = days * -1;
			if (days == 0) {
				return "Hoje";
			} else if (days == 1) {
				return "Ontem";
			} else {
				return days + " dias atrás";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public String formatPresentDate(Date dt) {
		long diff = DateUtil.getDiffMinutes(dt, new Date());

		if (diff == 1) {
			return "" + diff + " minuto atrás";
		}

		if (diff < 60) {
			return "" + diff + " minutos atrás";
		}

		diff = DateUtil.getDiffHours(dt, new Date());
		if (diff > 47) {
			return "" + DateUtil.getDiffDates(dt, new Date()) + " dias atrás";
		} else if (diff > 1) {
			return "" + diff + " horas atrás";
		} else {
			return "" + diff + " hora atrás";
		}
	}

	/**
	 * Formata um tipo Date de dentro do Velocity. Exemplo:
	 * 
	 * $vl.dateFormat($evento.getDataEvento())
	 * 
	 * @param date
	 * @return
	 */
	public String dateFormat(Date date) {
		try {
			return DateUtil.dateFormat(date);
		} catch (Exception e) {
			return "";
		}
	}

	public String dateFormat(Date date, String dtFrmt) {
		try {
			return DateUtil.dateFormat(date, dtFrmt);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Formata um tipo Date de dentro do Velocity. Exemplo:
	 * 
	 * $vl.dateMinuteFormat($evento.getDataEvento())
	 * 
	 * @param date
	 * @return
	 */
	public String dateMinuteFormat(Date date) {
		try {
			return DateUtil.dateMinuteFormat(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Formata a data no formato: Abr/2004 para o Velocity Exemplo:
	 * 
	 * $vl.dateAbrev($evento.getDataEvento())
	 * 
	 * @param date
	 * @return Uma string a data no formato: Abr/2004
	 */
	public String dateAbrev(Date date) {
		return DateUtil.getDataAbreviada(date);
	}

	/**
	 * Formata a data atual para apresentação conforme padrão definido do
	 * arquivo de configuração Exemplo:
	 * 
	 * $vl.dateNow()
	 * 
	 * @return a data atual formatada conforme padrão definido do arquivo de
	 *         configuração
	 */
	public String dateNow() {
		try {
			return DateUtil.dateFormat(new Date());
		} catch (RuntimeException e) {
			return "";
		}
	}

	/**
	 * Recupera data de hoje por extenso Ex: 17 de Março de 2005
	 * 
	 * Ex de utilização: $vl.dateExtended()
	 * 
	 * @return Data de Hoje por extenso
	 */
	public String dateExtended() {
		return DateUtil.getDataExtenso(new Date());
	}

	/**
	 * Recupera uma data por extenso. Ex: 17 de Março de 2005
	 * 
	 * Ex de utilização: $vl.dateExtended($evento.getDataEvento())
	 * 
	 * @param data
	 *            - Data a ser realizado o parser
	 * @return Data por extenso
	 */
	public String dateExtended(Date date) {
		return DateUtil.getDataExtenso(date);
	}

	/**
	 * @param parcial
	 *            Relative number
	 * @param total
	 *            The max number
	 * @return Relative Perncentage
	 */
	public static String getPercentage(double parcial, double total) {
		double p = parcial;
		double t = total;
		double perc = ((p * 100) / t);

		return new DecimalFormat("##.##").format(perc);
	}

	public String dateRFC() {
		return DateUtil.dateRFCFormat(new Date());
	}

	public String dateRFCFormat(Date date) {
		try {
			return DateUtil.dateRFCFormat(date);
		} catch (Exception e) {
			return "";
		}
	}

	public String removeHTML(String text) {
		/*
		 * if (text.indexOf('<') < 0) return text;
		 * 
		 * StringBuffer str = new StringBuffer();
		 * 
		 * while (text.length() > 0 && text.indexOf('<') >= 0) { if
		 * (text.indexOf('<') >= 0) { str.append(text.substring(0,
		 * text.indexOf('<'))); text = text.substring(text.indexOf('>') + 1); }
		 * }
		 * 
		 * str.append(text);
		 * 
		 * return str.toString();
		 */

		if (text != null) {
			text = text.replaceAll("\\<(.*?)\\>", "");
			text = text.replaceAll("--\\>.*?", "");
			text = text.replaceAll("\\>.*?", "");
			text = text.replaceAll("\\<.*?", "");
			return text;
		}
		return "";
	}

	public String substring(String txt, int init, int max) {
		if (txt == null) {
			return "";
		}
		if (txt.length() > max) {
			return txt.substring(init, max) + "...";
		} else {
			return txt;
		}
	}

	public String escapeBBcode(String str) {
		return ProcessBBCode.escapeBBcode(str);
	}

	/**
	 * Format a value in a Currency Format(e.g: 1.220,00)
	 * 
	 * @param param
	 * @return
	 */
	public String formatDigitValue(String param) {
		if (param == null || (new Integer(param).intValue() == 0)) {
			return "0";
		}
		try {
			return DigitFormat.parserValue(param);
		} catch (Exception e) {
			return "Render problem";
		}
	}

	public String formatFileName(String fileName) {
		if (fileName != null) {
			return fileName.substring(fileName.indexOf('-') + 1);
		} else {
			return "This fileName is null";
		}
	}

	public String captalizeTitle(String param) {
		try {
			param = param.toLowerCase();
			param = StringUtils.capitalize(param);
			return param;
		} catch (Exception e) {
			return "Render problem";
		}
	}

	public String parseStringTitle(String text) {
		if (text == null || "".equals(text)) {
			return "";
		}
		text = text.trim();
		text = text.replaceAll("\\-", "");
		text = text.replaceAll(" ", "-");
		text = text.replaceAll("-", "-");
		text = text.replaceAll("\\,", "");
		text = text.replaceAll("\\\"", "");
		text = text.replaceAll("\\!", "");
		text = text.replaceAll("\\?", "");
		text = text.replaceAll("\\.", "");
		text = text.replaceAll("\\[", "");
		text = text.replaceAll("\\]", "");
		text = text.replaceAll("\\)", "");
		text = text.replaceAll("\\(", "");
		text = text.replaceAll("\\/", "-");
		text = text.replaceAll("\\\\", "-");
		text = text.replaceAll(":", "-");
		text = text.replaceAll("\\|", "");
		text = text.replaceAll("\\%", "");
		text = text.replaceAll("\\>", "");
		text = text.replaceAll("\\<", "");
		text = text.replaceAll("\\$", "");
		text = text.replaceAll("\\!", "");
		text = text.replaceAll("\\?", "");
		text = text.replaceAll("\\;", "");
		text = text.replaceAll("\\\"", "");
		text = text.replaceAll("#", "");

		if (text.startsWith("-")) {
			text = text.substring(1, text.length());
		}
		if (text.endsWith("-")) {
			text = text.substring(0, text.length() - 1);
		}
		text = text.replaceAll("\\--", "-");
		text += ".html";

		return CharFilter.replaceSpecial(text);
	}

	public int getLength(String s) {
		return s.length();
	}

	public Integer getViewCountByTopicId(Long topicId, Integer viewNow) {
		Integer viewCached = CacheUtils.getViewTopicById(topicId);
		if (viewCached == null) {
			return viewNow;
		} else {
			return viewCached;
		}
	}

	public String escapeHTML(String str) {
		return (str != null) ? HtmlUtils.htmlEscape(str) : str;
	}
	
	public String getDownloadPath(String path){
		if (path == null){
			return "";
		} else {
			if(path.indexOf("files_user") > -1){
				return path.substring(path.indexOf("files_user")).replace('\\', '/');	
			} else {
				return path.substring(path.indexOf("files")).replace('\\', '/');
			}
			
		}
	}

}



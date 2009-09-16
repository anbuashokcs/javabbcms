package org.javabb.infra;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author Dalton Camargo - <a href="mailto:dalton@ag2.com.br">dalton@ag2.com.br
 *         </a> <Br>
 *         AG2 - Agencia de Inteligencia Digital S.A. <Br>
 *         <a href="http://www.ag2.com.br">http://www.ag2.com.br </a> <Br>
 *         Nosso <i>www </i> e mais <b>inteligente </b>!
 */
/*
$Id: DateUtil.java,v 1.1 2009/05/11 20:27:02 daltoncamargo Exp $
*/

/*http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html*/
public class DateUtil {

	public static SimpleDateFormat RFC822DATEFORMAT = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

	
    /**
     * Insere a hora e os minutos em uma Data
     * @param dateParam - Data a ser manipulada
     * @param hourMinute - Hora a ser incluída
     * @param format - separador entre hora e minuto, ex: 21:10 ou 21-20
     * @return - Retorna uma data com a hora e os minutos setados
     * @throws Exception - Caso aconteça algum erro, uma excessão é lançada
     */
    public static Date setDateHourMinute(Date dateParam, String hourMinute, String format) throws Exception{
	    String[] arrHourMinute = hourMinute.split(format);
		Calendar dteComp = Calendar.getInstance();
		dteComp.setTime(dateParam);
		dteComp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHourMinute[0]));
		dteComp.set(Calendar.MINUTE, Integer.parseInt(arrHourMinute[1]));
        return dteComp.getTime();
    }
    
    /**
     * Insere em uma determinada data, os segundos passados
     * @param dateParam - Data a ser retornada
     * @param second - Segundos a serem inseridos
     * @return - Data com os segundos já inseridos
     */
    public static Date setDateSecond(Date dateParam, int second){
        Calendar dteComp = Calendar.getInstance();
        dteComp.setTime(dateParam);
        dteComp.set(Calendar.SECOND, second);
        return dteComp.getTime();
    }

    /**
     * Recupera uma data por extenso.
     * Ex: 17 de Março de 2005
     * @param data - Data a ser realizado o parser
     * @return Data por extenso
     */
    public static String getDataExtenso(Date data){
    	String retorno = getDiaSemana(data) 
						+ ", " + getDiaDoMes(data) 
						+  " de " + getMesExtenso(data)
						+ " de "+ getAno(data);    	
    	return retorno;
    }
    
    
    /**
     * Metodo que obtém o dia da semana de uma determinada data
     * , ex: Segunda-Feira, Terça-Feira, etc..
     * @param data - Data a ser retirado o dia da semana
     * @return - String contendo o dia da semana
     */
    public static String getDiaSemana(Date data){
		String dt = "";
		try{
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == 1) dt = "Domingo";
			if(dayOfWeek == 2) dt = "Segunda-Feira";
			if(dayOfWeek == 3) dt = "Terça-Feira";
			if(dayOfWeek == 4) dt = "Quarta-Feira";
			if(dayOfWeek == 5) dt = "Quinta-Feira";
			if(dayOfWeek == 6) dt = "Sexta-Feira";
			if(dayOfWeek == 7) dt = "Sábado";
		} catch (Exception e){
		    e.printStackTrace();
		}
		return dt;  
    }

    
    /**
     * Metodo que retorna o mes por extenso, ex: Abril
     * @param data - Data a ser retirado o mes
     * @return - Uma string contendo o mes
     */
    public static String getMesExtenso(Date data){
		String dt = "";
		try{
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			int month = cal.get(Calendar.MONTH);
			if(month == 0) dt = "Janeiro";
			if(month == 1) dt = "Fevereiro";
			if(month == 2) dt = "Março";
			if(month == 3) dt = "Abril";
			if(month == 4) dt = "Maio";
			if(month == 5) dt = "Junho";
			if(month == 6) dt = "Julho";
			if(month == 7) dt = "Agosto";
			if(month == 8) dt = "Setembro";
			if(month == 9) dt = "Outubro";
			if(month == 10) dt = "Novembro";
			if(month == 11) dt = "Dezembro";

		} catch (Exception e){
			e.printStackTrace(); 
		}
		return dt;  
    }    

    /**
     * Metodo que retorna a data no formato: Abr/2004
     * @param data - Data a ser retirado o mes
     * @return - Uma string a data no formato: Abr/2004
     */
    public static String getDataAbreviada(Date data){
		String dt = "";
		try{
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			int month = cal.get(Calendar.MONTH);
			if(month == 0) dt = "Jan";
			if(month == 1) dt = "Fev";
			if(month == 2) dt = "Mar";
			if(month == 3) dt = "Abr";
			if(month == 4) dt = "Mai";
			if(month == 5) dt = "Jun";
			if(month == 6) dt = "Jul";
			if(month == 7) dt = "Ago";
			if(month == 8) dt = "Set";
			if(month == 9) dt = "Out";
			if(month == 10) dt = "Nov";
			if(month == 11) dt = "Dez";
			dt += "/" + cal.get(Calendar.YEAR);
		} catch (Exception e){
			e.printStackTrace(); 
		}
		return dt;  
    } 


    /**
     * Metodo retorna o dia do mes, ex: 12
     * @param data - Data a ser retirado o dia do mes
     * @return - String contendo o dia do mes
     */
    public static String getDiaDoMes(Date data){
		String dt = "";
		try{
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			dt = ""+ cal.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e){
		    e.printStackTrace();
		}
		return dt;  
    }

    /**
     * Metodo retorna o ano, ex: 2004
     * @param data - Data a ser retirado o ano
     * @return String contendo o ano
     */
    public static String getAno(Date data){
		String dt = "";
		try{
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			dt = ""+ cal.get(Calendar.YEAR);
		} catch (Exception e){
		    e.printStackTrace();
		}
		return dt;  
    }    

    /**
     * Retorna uma data formatada conforme a configuração de 
     * "Configuration.dateFormat" 
     * @param date
     * @return
     */
    public static String dateFormat(Date date){
        SimpleDateFormat sf = new SimpleDateFormat(ConfigurationFactory.getConf().dateFormat);
        return sf.format(date);
    }

    /**
     * Retorna uma data formatada conforme a configuração de 
     * "Configuration.dateFormat" 
     * @param date
     * @return
     */
    public static String dateMinuteFormat(Date date){
    	Configuration cfg = ConfigurationFactory.getConf();
    	String dtFmt = cfg.dateFormat;
    	String mntFmt = cfg.timeFormat;
        SimpleDateFormat sf = new SimpleDateFormat(dtFmt + " " + mntFmt);
        return sf.format(date);
    }

    /**
     * Retorna uma data formatada conforme String format 
     * passado
     * @param date
     * @return
     */
    public static String dateFormat(Date date, String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * Calcula o total de dias de um determinado mês
     * @param month
     * @param year
     * @return Retorna o total de dias de um determinado ano e mês
     */
    public static int getTotalOfDays(int month, int year) {
		int dom[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (year % 400 == 0) {
			dom[1] = 29;
		} else {
			if (year % 4 == 0 && year % 100 != 0) {
				dom[1] = 29;
			}
		}
		return dom[month - 1];
	}    
    
    
    public static String dateRFCFormat(Date date) {
        return RFC822DATEFORMAT.format(date);
    }
    
    public static long getDiffDates(Date dt, Date dt2){
    	Calendar cal1 = GregorianCalendar.getInstance();
    	cal1.setTime(dt);
    	Calendar cal2 = GregorianCalendar.getInstance();
    	cal2.setTime(dt2);
    	
    	
    	long diffMillis = cal2.getTimeInMillis()-cal1.getTimeInMillis();
     	return diffMillis /(24*60*60*1000);
    }
    
    public static long getDiffHours(Date dt, Date dt2){
    	Calendar cal1 = GregorianCalendar.getInstance();
    	cal1.setTime(dt);
    	Calendar cal2 = GregorianCalendar.getInstance();
    	cal2.setTime(dt2);
    	
    	
    	long diffMillis = cal2.getTimeInMillis()-cal1.getTimeInMillis();
     	return diffMillis/(60*60*1000);
    }
    public static long getDiffMinutes(Date dt, Date dt2){
    	Calendar cal1 = GregorianCalendar.getInstance();
    	cal1.setTime(dt);
    	Calendar cal2 = GregorianCalendar.getInstance();
    	cal2.setTime(dt2);
    	
    	
    	long diffMillis = cal2.getTimeInMillis()-cal1.getTimeInMillis();
     	return diffMillis/(60*1000);

    }

}

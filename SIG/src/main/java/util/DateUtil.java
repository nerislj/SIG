package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static String  getDateAtualYYYYMMDD(){
		Calendar cal = new GregorianCalendar();
		String mes = "0";
		String dia = "0";
		
		if ( cal.get(Calendar.MONTH)+1 < 10 ){
			mes += (cal.get(Calendar.MONTH)+1);
		}else{
			mes = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		
		if ( cal.get(Calendar.DATE) < 10 ){
			dia += cal.get(Calendar.DATE);
		}else{
			dia = String.valueOf(cal.get(Calendar.DATE));
		}
		return cal.get(Calendar.YEAR)+""+mes+""+dia;
	}
	
	
	public static String  getAnoMes(){
		Calendar cal = new GregorianCalendar();
		String mes = "0";
		if ( cal.get(Calendar.MONTH)+1 < 10 ){
			mes += (cal.get(Calendar.MONTH)+1);
		}else{
			mes = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		return cal.get(Calendar.YEAR)+""+mes;
	}
	
	public static String getAno() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }
	
	public static String  getDateAtualFormatada(){
		Calendar cal = new GregorianCalendar();
		String mes = "0";
		String dia = "0";
		
		if ( cal.get(Calendar.MONTH)+1 < 10 ){
			mes += (cal.get(Calendar.MONTH)+1);
		}else{
			mes = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		
		if ( cal.get(Calendar.DATE) < 10 ){
			dia += cal.get(Calendar.DATE);
		}else{
			dia = String.valueOf(cal.get(Calendar.DATE));
		}
		return dia+ "/" +mes+"/"+cal.get(Calendar.YEAR);
	}
	
	public static String  getDateFormatada(Date data){
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		String mes = "0";
		String dia = "0";
		
		if ( cal.get(Calendar.MONTH)+1 < 10 ){
			mes += (cal.get(Calendar.MONTH)+1);
		}else{
			mes = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		
		if ( cal.get(Calendar.DATE) < 10 ){
			dia += cal.get(Calendar.DATE);
		}else{
			dia = String.valueOf(cal.get(Calendar.DATE));
		}
		return dia+ "/" +mes+"/"+cal.get(Calendar.YEAR);
	}
	
	
	
	
	 /** 
	 * Retorna a diferenca de dias entre duas datas.
	 * @param c1 
	 * @param c2
	 * @returnX
	 */
	public static int diferencaEmDias(Calendar c1, Calendar c2) {
	    long m1 = c1.getTimeInMillis();
	    long m2 = c2.getTimeInMillis();
	    return (int) ((m2 - m1) / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * Adiciona dias a uma data.
	 * @param date
	 * @param dias
	 * @return
	 */
	public static Date adicionaDias(Date date, int dias) {
	
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE, dias);
	
	    return calendar.getTime();
	
	}
	
	public Date getDataAtual(){
	    return new Date();
	}
	
	
	public static void main(String args[]){
		System.out.println(getDateAtualYYYYMMDD());
		System.out.println(getDateAtualFormatada());
		System.out.println(getDateFormatada(new Date()));
	}
}

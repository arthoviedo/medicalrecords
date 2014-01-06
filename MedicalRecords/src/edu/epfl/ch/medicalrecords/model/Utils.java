package edu.epfl.ch.medicalrecords.model;

import java.util.Calendar;

public class Utils {

	public static String getFechaAsString(Calendar fecha){
		return fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR)+" " +
				fecha.get(Calendar.HOUR_OF_DAY)+":"+ ((fecha.get(Calendar.MINUTE))<10?"0":"")+ (fecha.get(Calendar.MINUTE)) ;
	}
}

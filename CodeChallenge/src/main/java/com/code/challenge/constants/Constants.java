package com.code.challenge.constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {

	public static String COMMA =",";
	public static String TRANSACTION_DATE_PATTERN = "dd/MM/yyyy hh:mm:ss";
	public static SimpleDateFormat TRANSACTION_DATE_FORMATTER = new SimpleDateFormat(TRANSACTION_DATE_PATTERN);
	public static DecimalFormat DecimalFormat =new DecimalFormat("#.##");
	
	
}

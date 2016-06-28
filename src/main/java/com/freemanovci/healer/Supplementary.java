package com.freemanovci.healer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Supplementary {
	
	//STOLEN CODE, HEHE
	//actually, here's the stackoverflow topic: http://stackoverflow.com/questions/801935/formatting-file-sizes-in-java-jstl
	//I was too lazy to think of this code
	//So I just edited it
	public static String humanReadableEnergy(long number){
		System.out.println("number = "+number);
		long absNumber = Math.abs(number);
		System.out.println("absNumber = "+absNumber);
		double result = number;
		String suffix = "";
		if(absNumber < 1000){
			//it's okay, defaulting
			suffix = "J";
		}else if(absNumber < 1000*1000){
			result = number / 1000d;
			suffix = "kJ";
		}else if(absNumber < 1000*1000*1000){
			result = number / (1000d*1000d);
			suffix = "MJ";
		}else{
			result = number / (1000d*1000d*1000d);
			suffix = "GJ";
		}
		//This piece is actually from here :D
		//https://kodejava.org/how-do-i-format-a-number/
		NumberFormat formatter = new DecimalFormat("#0.0");
		return (formatter.format(result)+" "+suffix);
	}
}

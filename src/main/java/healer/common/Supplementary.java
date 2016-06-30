package healer.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

public class Supplementary {
	
	//STOLEN CODE, HEHE
	//actually, here's the stackoverflow topic: http://stackoverflow.com/questions/801935/formatting-file-sizes-in-java-jstl
	//I was too lazy to think of this code
	//So I just edited it
	public static String humanReadableEnergy(long number){
		//System.out.println("number = "+number);
		long absNumber = Math.abs(number);
		//System.out.println("absNumber = "+absNumber);
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
	
	//COPIED CODE
	//SOURCE: http://stackoverflow.com/questions/7528045/large-string-split-into-lines-with-maximum-length-in-java
	public static String[] breakIntoLines(String text, int maxLineLength){
		String SPACE_SEPARATOR = " ";
		String SPLIT_REGEXP = "\\s+";
		char NEWLINE = '\n';
		String[] tokens = text.split(SPLIT_REGEXP);
	    StringBuilder output = new StringBuilder(text.length());
	    int lineLen = 0;
	    for (int i = 0; i < tokens.length; i++) {
	        String word = tokens[i];

	        if (lineLen + (SPACE_SEPARATOR + word).length() > maxLineLength) {
	            if (i > 0) {
	                output.append(NEWLINE);
	            }
	            lineLen = 0;
	        }
	        if (i < tokens.length - 1 && (lineLen + (word + SPACE_SEPARATOR).length() + tokens[i + 1].length() <=
	                maxLineLength)) {
	            word += SPACE_SEPARATOR;
	        }
	        output.append(word);
	        lineLen += word.length();
	    }
	    String withLinebreaks = output.toString();
	    String lines[] = withLinebreaks.split("\\n");
	    return lines;
	}
}

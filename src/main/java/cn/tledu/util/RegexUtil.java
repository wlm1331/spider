package cn.tledu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	public static String match(String input, String regex){
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			String result = m.group();
			sb.append(result);
		}
		return sb.toString();
	}
	
	public static List<String> matchList(String input, String regex){
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			String result = m.group();
			list.add(result);
		}
		return list;
	}
	
	public static String match(String input, String regex, int grpNUM){
		String result = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			result = m.group(grpNUM);
		}
		return result;
	}

}

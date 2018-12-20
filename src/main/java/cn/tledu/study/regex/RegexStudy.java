package cn.tledu.study.regex;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tledu.util.RegexUtil;
import cn.tledu.util.WebUtil;

public class RegexStudy {
	public static void testWebUtil(){
		String urlStr="http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml";
		String encoding = "gb2312";//utf-8
		String input = WebUtil.urlGetString(urlStr, encoding);
		//System.out.println(input);
		String ulregex = "<ul class=\"list_009\">[\\s\\S]*?</ul>";//核心
		String ulresult = RegexUtil.match(input, ulregex);
		//System.out.println(result);
		String liregex = "<li>[\\s\\S]*?</li>";// ?问号不能不写
		List<String>list = RegexUtil.matchList(ulresult, liregex);
		for(String str :list){
			//System.out.println(str);
			//<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\S\\s]*?)</a><span>\\(([\\S]*?) [\\S]*?\\)</span></li>
			String grpRegex="<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\s\\S]*?)</a><span>\\(([\\S]*?) [\\S]*?\\)</span></li>";
			String liUrlstr = RegexUtil.match(str, grpRegex, 1);
			String liTitle = RegexUtil.match(str, grpRegex, 2);
			String liDate = RegexUtil.match(str, grpRegex, 3);
			System.out.println(liUrlstr+"\t"+liTitle+"\t"+liDate);
		}
		
	}
	
	public static void test(){
		String regex ="([a-zA-Z]{1,})([0-9]{1,})";//模式字符串
		Pattern p = Pattern.compile(regex);
		String input = "abc1234abcdefg5678";//一大堆原始字符串
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			String result1 = m.group(1);
			String result2 = m.group(2);
			System.out.println(result1+"\t"+result2);
		}
		
	}
	
	//分组
	public static void test2(){
		String regex ="([a-zA-Z]{1,})([0-9]{1,})";//模式字符串
		Pattern p = Pattern.compile(regex);
		String input = "abc1234abcdefg5678";//一大堆原始字符串
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			String result1 = m.group(1);
			String result2 = m.group(2);
			System.out.println(result1+"\t"+result2);
		}
	}
	
	
	public static void test1(){
		String regex ="\\d{1,}";//模式字符串
		Pattern p = Pattern.compile(regex);
		String input = "abc1234abcdefg5678";//一大堆原始字符串
		Matcher m = p.matcher(input);
		while(m.find()){  //if:只找一次    while：找多次，一直找到头 
			String result = m.group();
			System.out.println(result);
		}
		
	}

	public static void main(String[] args) {
		//test();
		testWebUtil();

	}

}

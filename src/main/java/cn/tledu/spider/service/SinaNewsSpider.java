package cn.tledu.spider.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import cn.tledu.spider.pojo.SinaNewsPojo;
import cn.tledu.util.CloseUtil;
import cn.tledu.util.IOUtil;
import cn.tledu.util.MySynQueue;
import cn.tledu.util.RegexUtil;
import cn.tledu.util.WebUtil;

public class SinaNewsSpider {
	public static void multiThreadCrawler(){
		MySynQueue<SinaNewsPojo> queue = new MySynQueue<SinaNewsPojo>();
		//爬取列表页
		int page=1;
		String urlStr="http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml";
		String encoding="gb2312";
		for(int i =0;i<page;i++){
			//爬单页列表页的逻辑
			int pageNum = i+1;
			if(pageNum >= 2){
				urlStr="http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_"+i+".shtml";
			}
			Thread crawlerNewList = new Thread(new NewsProducer(urlStr, encoding,queue));
			crawlerNewList.start();
			//crawler(urlStr,encoding);
		}
		//爬详情页（爬网页  保存为文件）
		Thread crawlerDetail = new Thread(new NewsConsumer(queue));
		crawlerDetail.start();
	}
	public static void crawler(String urlStr,String encoding,MySynQueue<SinaNewsPojo> queue){
		String input = WebUtil.urlGetString(urlStr, encoding);
		String ulregex = "<ul class=\"list_009\">[\\s\\S]*?</ul>";//核心
		String ulresult = RegexUtil.match(input, ulregex);
		String liregex = "<li>[\\s\\S]*?</li>";// ?问号不能不写
		List<String>list = RegexUtil.matchList(ulresult, liregex);
		System.out.println("需要爬取"+list.size()+"条");
		for(String str :list){
			String grpRegex="<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\s\\S]*?)</a><span>\\(([\\S]*?) [\\S]*?\\)</span></li>";
			String liUrlStr = RegexUtil.match(str, grpRegex, 1);
			String liTitle = RegexUtil.match(str, grpRegex, 2);
			String liDate = RegexUtil.match(str, grpRegex, 3);
			queue.offer(new SinaNewsPojo(liUrlStr,liTitle,liDate));
		}
	}
	
	 
	public static void crawer(){
		//爬虫列表页
		String urlStr="http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_1.shtml";
		String encoding = "gb2312";//utf-8
		String input = WebUtil.urlGetString(urlStr, encoding);
		String ulregex = "<ul class=\"list_009\">[\\s\\S]*?</ul>";//核心
		String ulresult = RegexUtil.match(input, ulregex);
		String liregex = "<li>[\\s\\S]*?</li>";// ?问号不能不写
		List<String>list = RegexUtil.matchList(ulresult, liregex);
		System.out.println("需要爬取"+list.size()+"条");
		int count = 0;
		for(String str :list){
			/*if(count>2){
				break;
			}*/
			System.out.println("正在爬取第"+(count+1)+"条...");
			String grpRegex="<li><a href=\"([\\S]*?)\" target=\"_blank\">([\\s\\S]*?)</a><span>\\(([\\S]*?) [\\S]*?\\)</span></li>";
			String liUrlStr = RegexUtil.match(str, grpRegex, 1);
			String liTitle = RegexUtil.match(str, grpRegex, 2);
			String liDate = RegexUtil.match(str, grpRegex, 3);
			detailProcessor(liUrlStr,liTitle,liDate);
			count++;
		}
    }
	
	public static void detailProcessor(String liUrlStr, String liTitle,String liDate){
		//爬取详情页
		byte[]arr =SinaNewsSpider.detailCrawer(liUrlStr);
		//按日期创建对应目录
		//一天对应一个目录  每天对应的目录下  均有一对文件
		//一个数据文件  一个索引文件
		//D：\\spider_db\\08月31日\\
		String fileBaseDir = "D:" + File.separator + "spider_db" + File.separator + liDate + File.separator;
		File fileBaseDirObj = new File(fileBaseDir);
		if(!fileBaseDirObj.exists()){
			//mkdirs D:\\abc\\123   自动创建,别用mkdir
			fileBaseDirObj.mkdirs();
		}
		String dataPath = fileBaseDir+"spider_data.dat";
		String indexPath = fileBaseDir+"spider_index.txt";
		File dataFile = new File(dataPath);
		long dataFileLen = dataFile.length();
		int dataByteLen = arr.length;
		StringBuffer sb = new StringBuffer();
		char spiltChar = '\u0001';
		sb.append(liUrlStr).append(spiltChar)
		.append(liTitle).append(spiltChar)
		.append(liDate).append(spiltChar)
		.append(dataFileLen).append(spiltChar)
		.append(dataByteLen);
		
		//存储为数据文件索引文件
        IOUtil.writeDataFile(arr,dataPath);
        IOUtil.writeIndexFile(sb.toString(),indexPath);
	}
	
	
	
	public static byte[] detailCrawer(String urlStr){
		//String urlStr = "http://news.sina.com.cn/c/nd/2018-09-09/doc-ihivtsyk3141721.shtml";
		//String encoding = "utf-8";
		return WebUtil.urlGetByteArray(urlStr);
	}
	
	
	
}

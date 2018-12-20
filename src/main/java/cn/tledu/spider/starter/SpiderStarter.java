package cn.tledu.spider.starter;

import java.io.File;

import cn.tledu.spider.service.SinaNewsSpider;

public class SpiderStarter {

	public static void main(String[] args) {
		//SinaNewsSpider.crawer();
		//System.out.println("done");
		SinaNewsSpider.multiThreadCrawler();
	}

}

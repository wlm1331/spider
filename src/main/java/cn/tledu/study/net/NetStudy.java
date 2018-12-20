package cn.tledu.study.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cn.tledu.util.CloseUtil;

public class NetStudy {
	public static void test(){
		//URL,URLConnection
		//Uniformed Resource Location
		//HTTP Hyper Text Transfer Protocol
		String urlStr="http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml";
		String encoding = "gb2312";//utf-8
		URL url = null;
		URLConnection conn =null;
		BufferedReader br =null; 
		try {
			url=new URL(urlStr);
			conn = url.openConnection();//先建立本地到远程的连接
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine())!= null){
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CloseUtil.close(br);
		}
	}

	public static void main(String[] args) {
		test();

	}
}

package cn.tledu.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebUtil {
	public static String urlGetString(String urlStr,String encoding){
		String result = null;
		StringBuffer sb = new StringBuffer();
		URL url = null;
		URLConnection conn =null;
		BufferedReader br =null; 
		try {
			url=new URL(urlStr);
			conn = url.openConnection();//先建立本地到远程的连接
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine())!= null){
				//System.lineSeparator() 自动添加操作系统对应的换行符
				sb.append(line).append(System.lineSeparator());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CloseUtil.close(br);
		}
		result = sb.toString();
		return result;
	}
	
	public static byte[] urlGetByteArray(String urlStr){
		URL url = null;
		URLConnection conn =null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis =null;
		byte[] byteArray =new byte[0];
		try {
			url=new URL(urlStr);
			conn = url.openConnection();//先建立本地到远程的连接
			bis = new BufferedInputStream(conn.getInputStream());
			int b = -1;
			while ((b = bis.read())!= -1){
				baos.write(b);
			}
			byteArray =baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CloseUtil.close(bis);
			CloseUtil.close(baos);
		}
		return byteArray;
	}
}

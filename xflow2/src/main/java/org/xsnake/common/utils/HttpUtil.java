package org.xsnake.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpUtil {
	public static String get(String url) throws IOException{
		return get(url,null);
	}
	public static String get(String url,Map<String,String> params) throws IOException{
		String result = null;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpContext context = new BasicHttpContext();
			if(params !=null){
				for(Entry<String,String> entry : params.entrySet()){
					context.setAttribute(entry.getKey(), entry.getValue());
				}
			}
			httpGet.setHeader(new BasicHeader("accept", "application/json"));
			CloseableHttpResponse response = httpClient.execute(httpGet,context);
			try {
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					result = sb.toString().trim();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return result;
	}
	
	public static String post(String url,Map<String,String> params,String postContent) throws IOException{
		String result = null;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			//Post数据
			HttpPost httpPost = new HttpPost(url);
			HttpEntity postEntity = new ByteArrayEntity(postContent.getBytes());
			httpPost.setEntity(postEntity);
			//设置参数
			HttpContext context = new BasicHttpContext();
			for(Entry<String,String> entry : params.entrySet()){
				context.setAttribute(entry.getKey(), entry.getValue());
			}
			CloseableHttpResponse response = httpClient.execute(httpPost,context);
			try {
				HttpEntity entity = response.getEntity();// 得到请求的尸体
				InputStream is = entity.getContent();// 得到请求的内容
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					result = sb.toString().trim();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return result;
	}
	
}

package com.fish.server.base.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpClientUtil {

	public static String get(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			if (params.size() > 0) {
				String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
				try {
					httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			return "";
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String post(String url, List<NameValuePair> params) {
		// TODO Auto-generated method stub

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpPost httpost = new HttpPost(url);
			// 设置参数
			httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpost);

			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return url;
	}

	public static Map toMap(String content) {
		Map<String, Map<String, Object>> map = null;
		try {
			if (content != null && !content.equals("")) {
				ObjectMapper objectMapper = new ObjectMapper();
				map = objectMapper.readValue(content, Map.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = null;
		}
		return map;
	}

}

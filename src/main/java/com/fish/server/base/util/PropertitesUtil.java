package com.fish.server.base.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.StringUtils;

public class PropertitesUtil {

	private static Map maps = new HashMap();

	public static String VERSION_PROPERTITES = "version.properties";
	public static String PROJECT_PROPERTITES = "project.properties";

	// 根据key读取value
	public static String readValue(String filePath, String key) {
		MyPropertites props = null;
		if (maps.get(filePath) != null) {
			props = (MyPropertites) maps.get(filePath);
		} else {
			try {
				props = new MyPropertites();
				InputStream inputStream = props.getClass().getClassLoader().getResourceAsStream(filePath);
				props.load(inputStream);
				maps.put(filePath, props);
			} catch (IOException e1) {

				maps.put(filePath, null);
			}
		}
		String value = props.getProperty(key);
		return value;
	}

	// 读取properties的全部信�?
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方�?put。使�?getProperty 方法提供并行性�?
			// 强制要求为属性的键和值使用字符串。返回�?�?Hashtable 调用 put 的结果�?
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以�?合使�?load 方法加载�?Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出�?
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}

	public static void main(String[] args) {
		readValue("project.properties", "msgserver");

	}

	public static int readIntValue(String filePath, String key) {
		String value = readValue(filePath, key);
		if (!StringUtils.isEmpty(value))
			return Integer.valueOf(value);
		return 0;
	}
}
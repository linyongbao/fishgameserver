package com.fish.server.base.util;

/* 
* Created on 2005-6-5 
* Author stephen 
* Email zhoujianqiang AT gmail DOT com 
* CopyRight(C)2005-2008 , All rights reserved. 
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 与系统相关的一些常用工具方法.
 * 
 * @author stephen
 * @version 1.0.0
 */
public class SystemTool {

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				/**
				 * 找到了
				 */
				if (index != -1) {
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + 4).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡
																	// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]
				if (index >= 0) {// 找到了
					mac = line.substring(index + "hwaddr".length() + 1).trim();// 取出mac地址并去除2边空格
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = "";
		BufferedReader bufferedReader = null;
		Process process = null;

		int mPhysicalMacNumber = 0;
		boolean isInit = false;

		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
			String line = null;
			Pattern macPattern =

					Pattern.compile("([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}");

			Matcher macMatcher;

			boolean result;
			while ((line = bufferedReader.readLine()) != null) {
				if ("".equals(line))

					continue;

				macMatcher = macPattern.matcher(line);

				result = macMatcher.find();

				if (result)

				{

					mPhysicalMacNumber++;

					if ("".equals(mac))

						mac = macMatcher.group(0);

					else

						mac += ("," + macMatcher.group(0));

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 测试用的main方法.
	 * 
	 * @param argc
	 *            运行参数.
	 */
	public static String getMac() {
		String os = getOSName();
		String mac = "";
		System.out.println(os);
		if (os.startsWith("windows")) {
			mac = getWindowsMACAddress();
			System.out.println("本地是windows:" + mac);
		} else if (os.startsWith("linux")) {
			mac = getLinuxMACAddress();
			System.out.println("本地是Linux系统,MAC地址是:" + mac);
		} else {
			mac = getUnixMACAddress();
			System.out.println("本地是Unix系统 MAC地址是:" + mac);
		}

		return mac;
	}
}
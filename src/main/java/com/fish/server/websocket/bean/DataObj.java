package com.fish.server.websocket.bean;

import io.netty.util.internal.StringUtil;

public class DataObj {

	private String fromUid;

	public String getFromUid() {
		return fromUid;
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	/**
	 * @return the toUid
	 */
	public String getToUid() {
		return toUid;
	}

	/**
	 * @param toUid
	 *            the toUid to set
	 */
	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the cmd
	 */
	public int getCmd() {
		return cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	private String toUid;

	public String getToUids() {
		return toUids;
	}

	public void setToUids(String toUids) {
		this.toUids = toUids;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	private Object jsonObj;

	/**
	 * @return the jsonObj
	 */
	public Object getJsonObj() {
		return jsonObj;
	}

	/**
	 * @param jsonObj
	 *            the jsonObj to set
	 */
	public void setJsonObj(Object jsonObj) {
		this.jsonObj = jsonObj;
	}

	private Long time;
	private int cmd = 0;
	private int serviceId = 0;
	private int code = 0;// 不成功
	private String notice;
	public String toUids;

	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @param notice
	 *            the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @return the serviceid
	 */
	public int getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceid
	 *            the serviceid to set
	 */
	public void setServiceid(int serviceId) {
		this.serviceId = serviceId;
	}

	public void setCode(int code) {
		// TODO Auto-generated method stub
		this.code = code;
	}

	public int getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	public String[] toUidsArr() {
		// TODO Auto-generated method stub
		if (!StringUtil.isNullOrEmpty(this.toUids))
			return this.toUids.split(",");
		return null;
	}

}

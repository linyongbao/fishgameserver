package com.fish.server.web.file;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		Map status = new HashMap();
		session.setAttribute("upload_status", status);
	}

	/*
	 * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Map status = (Map) session.getAttribute("upload_status");
		status.put("bytesRead", pBytesRead);
		status.put("bytesTotal", pContentLength);
		status.put("pItems", pItems);
	}

}
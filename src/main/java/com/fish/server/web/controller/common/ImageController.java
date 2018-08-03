package com.fish.server.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fish.server.base.util.PropertitesUtil;
import com.fish.server.web.controller.base.BaseController;

@Controller
@RequestMapping("/image")
public class ImageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@RequestMapping("/gok4")
	@ResponseBody
	public Object gok4(HttpServletRequest request, @RequestParam("uploadfile") MultipartFile uploadfile)
			throws Exception, IOException {
		String webUrl = request.getSession().getServletContext().getRealPath("/") + "/";
		String targetDirRelatePath = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "upload.dir");
		String orgName = uploadfile.getOriginalFilename();
		String pr;
		String name_nopr;
		if (orgName != null && !orgName.equals("null")) {
			pr = orgName.substring(orgName.lastIndexOf("."));
			name_nopr = orgName.substring(0, orgName.lastIndexOf("."));
		} else {
			pr = ".png";
			name_nopr = System.currentTimeMillis() + "";
		}

		String dirAbsolutPath = webUrl + targetDirRelatePath;
		File tempFile = new File(dirAbsolutPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		String newName = name_nopr + pr;
		String fileAbsolutPath = dirAbsolutPath + "/" + newName;

		logger.info("目标文件:" + fileAbsolutPath);
		tempFile = new File(fileAbsolutPath);
		if (!tempFile.exists()) {
			tempFile.createNewFile();
			uploadfile.transferTo(tempFile);
			logger.info("复制文件成功");
		}
		Map res = new HashMap();
		res.put("url", targetDirRelatePath + "/" + newName);
		res.put("error", 0);
		return res;
	}

}

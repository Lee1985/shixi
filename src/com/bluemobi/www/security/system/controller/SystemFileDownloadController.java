package com.bluemobi.www.security.system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemFullTextService;

/**
 * 
* @ClassName: SystemFileDownloadController
* @Description: 文件下载
* @author sundq 
* @date 2016-02-14 13:32:36 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemFileDownloadController extends BaseController {
	@Resource
	private SystemFullTextService service;

	@RequestMapping(value = "/system/fileDownload")
	@ResponseBody
	public String fileDownload(HttpServletRequest request,
			HttpServletResponse response, String url) {
		response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + url);
        try {
            String path = request.getRealPath("/");
            InputStream inputStream = new FileInputStream(new File(path + url));
 
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}

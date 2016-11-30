package com.bluemobi.www.security.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface SystemFileInfoService extends BaseService<SystemFileInfo>{
	
	/**
	 * 新增文件
	 * @param file
	 * @param filePath
	 * @return
	 */
	public SystemFileInfo insertFileInfo(MultipartFile file, String filePath, String webPath);

	/**
	 * 根据多个uuid查询文件列表
	 * @param videoIdList
	 * @return
	 */
	public List<SystemFileInfo> selectByUuids(List<String> videoIdList);
	
	/**
	 * 根据uuid查询文件信息
	 * @param uuid
	 * @return
	 */
	public SystemFileInfo selectByUuid(String uuid);

}

package com.bluemobi.www.security.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface SystemPictureInfoService extends BaseService<SystemPictureInfo>{
	
	/**
	 * 新增文件
	 * @param file
	 * @param filePath
	 * @return
	 */
	public SystemPictureInfo insertFileInfo(MultipartFile file, String filePath, String webPath);

	public SystemPictureInfo selectEntityByUuid(String uuid);
	/**
	 * 根据多个uuid查询文件列表
	 * @param videoIdList
	 * @return
	 */
	public List<SystemPictureInfo> selectByUuids(List<String> uuidList);
	
	/**
	 * 根据uuid查询文件信息
	 * @param uuid
	 * @return
	 */
	public SystemPictureInfo selectByUuid(String uuid);

}

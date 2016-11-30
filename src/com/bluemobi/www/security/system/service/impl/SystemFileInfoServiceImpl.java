package com.bluemobi.www.security.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.data.dao.system.SystemFileInfoDao;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

@Service
public class SystemFileInfoServiceImpl extends BaseServiceImpl<SystemFileInfo> implements SystemFileInfoService{
	
	@Autowired
	private SystemFileInfoDao systemFileInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemFileInfoDao);
	}
	
	@Override
	public SystemFileInfo insertFileInfo(MultipartFile file, String filePath, String webPath) {		
		FileInfo fileInfo = FileTool.saveFile(file, filePath);
		String urlPath = webPath + fileInfo.getRealName();
		SystemFileInfo systemFileInfo = new SystemFileInfo();
		systemFileInfo.setId(UUIDUtil.getUUID());
		systemFileInfo.setUuid(UUIDUtil.getUUID());
		systemFileInfo.setUrlPath(urlPath);
		systemFileInfo.setFsize(fileInfo.getFsize());
		systemFileInfo.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemFileInfo.setSuffix(fileInfo.getSuffix());
		systemFileInfo.setName(fileInfo.getRealName());
		int result = systemFileInfoDao.insert(systemFileInfo);
		if(result > 0){
			return systemFileInfo;
		}else{
			return null;
		}
	}
	
	@Override
	public List<SystemFileInfo> selectByUuids(List<String> videoIdList) {
		return systemFileInfoDao.selectByUuids(videoIdList);
	}

	@Override
	public SystemFileInfo selectByUuid(String uuid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuid);
		List<SystemFileInfo> fileList = selectAll(params);
		SystemFileInfo systemFileInfo = null;
		if(fileList != null && !fileList.isEmpty()){
			systemFileInfo = fileList.get(0);
		}		
		return systemFileInfo;
	}
	
}
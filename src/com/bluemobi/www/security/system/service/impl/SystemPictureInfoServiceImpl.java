package com.bluemobi.www.security.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.data.dao.system.SystemPictureInfoDao;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

@Service
public class SystemPictureInfoServiceImpl extends BaseServiceImpl<SystemPictureInfo> implements SystemPictureInfoService{

	@Autowired
	private SystemPictureInfoDao systemPictureInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemPictureInfoDao);
	}

	@Override
	public SystemPictureInfo selectEntityByUuid(String uuid) {
		return systemPictureInfoDao.selectEntityByUuid(uuid);
	}
	
	@Override
	public SystemPictureInfo insertFileInfo(MultipartFile file, String filePath,String webPath) {
		FileInfo fileInfo = FileTool.saveFile(file, filePath);
		String urlPath = webPath + fileInfo.getRealName();
		SystemPictureInfo systemPicInfo = new SystemPictureInfo();
		systemPicInfo.setId(UUIDUtil.getUUID());
		systemPicInfo.setUuid(UUIDUtil.getUUID());
		systemPicInfo.setUrlPath(urlPath);
		systemPicInfo.setFsize((int)fileInfo.getFsize());
		systemPicInfo.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemPicInfo.setSuffix(fileInfo.getSuffix());
		int result = systemPictureInfoDao.insert(systemPicInfo);
		if(result > 0){
			return systemPicInfo;
		}else{
			return null;
		}
	}

	@Override
	public List<SystemPictureInfo> selectByUuids(List<String> uuidList) {
		return systemPictureInfoDao.selectByUuids(uuidList);
	}

	@Override
	public SystemPictureInfo selectByUuid(String uuid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uuid", uuid);
		List<SystemPictureInfo> fileList = selectAll(params);
		SystemPictureInfo systemPictureInfo = null;
		if(fileList != null && !fileList.isEmpty()){
			systemPictureInfo = fileList.get(0);
		}
		return systemPictureInfo;
	}
	
}
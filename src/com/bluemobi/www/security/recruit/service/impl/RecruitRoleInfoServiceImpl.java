package com.bluemobi.www.security.recruit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitRoleInfoDao;
import com.bluemobi.www.data.dao.system.SystemPictureInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;

@Service
public class RecruitRoleInfoServiceImpl extends BaseServiceImpl<RecruitRoleInfo> implements RecruitRoleInfoService{

	@Autowired
	private RecruitRoleInfoDao recruitRoleInfoDao;
	
	@Autowired
	private SystemPictureInfoDao systemPictureInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitRoleInfoDao);
	}

	@Override
	public List<RecruitRoleInfo> selectAllWithImage(Map<String, Object> params) {
		List<RecruitRoleInfo> roleList = selectAll(params);
		if(roleList != null){
			List<String> imageIdList = new ArrayList<String>();
			for(RecruitRoleInfo recruitRoleInfo : roleList){
				imageIdList.add(recruitRoleInfo.getImgUuid());				
			}
			List<SystemPictureInfo> picList = systemPictureInfoDao.selectByUuids(imageIdList);
			if(picList == null){
				return roleList;
			}
			Map<String,SystemPictureInfo> picMap = new HashMap<String,SystemPictureInfo>();
			for(SystemPictureInfo systemPictureInfo : picList){
				picMap.put(systemPictureInfo.getUuid(), systemPictureInfo);
			}
			for(RecruitRoleInfo recruitRoleInfo : roleList){
				SystemPictureInfo systemPictureInfo = picMap.get(recruitRoleInfo.getImgUuid());
				if(systemPictureInfo != null){
					recruitRoleInfo.setPictureInfo(systemPictureInfo);
				}								
			}
		}				
		return roleList;
	}

	@Override
	public List<RecruitRoleInfo> selectByRecruitIdsWithPictureInfo(List<String> idList) {
		List<RecruitRoleInfo> roleList = recruitRoleInfoDao.selectByRecruitIds(idList);
		if(roleList != null){
			List<String> imageIdList = new ArrayList<String>();
			for(RecruitRoleInfo recruitRoleInfo : roleList){
				imageIdList.add(recruitRoleInfo.getImgUuid());				
			}
			List<SystemPictureInfo> picList = systemPictureInfoDao.selectByUuids(imageIdList);
			if(picList == null){
				for(RecruitRoleInfo recruitRoleInfo : roleList){					
					recruitRoleInfo.setImageUrl("");				
				}
				return roleList;
			}
			Map<String,SystemPictureInfo> picMap = new HashMap<String,SystemPictureInfo>();
			for(SystemPictureInfo systemPictureInfo : picList){
				picMap.put(systemPictureInfo.getUuid(), systemPictureInfo);
			}
			for(RecruitRoleInfo recruitRoleInfo : roleList){
				SystemPictureInfo systemPictureInfo = picMap.get(recruitRoleInfo.getImgUuid());
				if(systemPictureInfo == null){
					recruitRoleInfo.setImageUrl("");
				}else{
					if(StringUtils.isNotBlank(systemPictureInfo.getUrlPath())){
						recruitRoleInfo.setImageUrl(systemPictureInfo.getUrlPath());
					}else{
						recruitRoleInfo.setImageUrl("");
					}						
				}								
			}
		}
		return roleList;
	}
	
}
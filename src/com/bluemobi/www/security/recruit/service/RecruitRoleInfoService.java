package com.bluemobi.www.security.recruit.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface RecruitRoleInfoService extends BaseService<RecruitRoleInfo>{
	
	/**
	 * 根据查询条件批量查询招募角色信息(自带角色图片)
	 * @param params
	 * @return
	 */
	public List<RecruitRoleInfo> selectAllWithImage(Map<String,Object> params);
	
	/**
	 * 根据招募id列表批量查询角色信息(自带角色图片)
	 * @param idList
	 * @return
	 */
	public List<RecruitRoleInfo> selectByRecruitIdsWithPictureInfo(List<String> idList);	

}

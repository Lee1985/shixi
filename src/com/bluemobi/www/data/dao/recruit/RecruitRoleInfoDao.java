package com.bluemobi.www.data.dao.recruit;

import java.util.List;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
/**
 * 数据访问接口
 *
 */
public interface RecruitRoleInfoDao extends BaseDao<RecruitRoleInfo>{
	public String sqlNameSpace=RecruitRoleInfoDao.class.getName();

	/**
	 * 根据招募id列表批量查询角色信息
	 * @param idList
	 * @return
	 */
	List<RecruitRoleInfo> selectByRecruitIds(List<String> idList);
}
package com.bluemobi.www.data.dao.system;

import java.util.List;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemFileInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemFileInfoDao extends BaseDao<SystemFileInfo>{
	public String sqlNameSpace=SystemFileInfoDao.class.getName();

	/**
	 * 根据多个uuid查询文件列表
	 * @param videoIdList
	 * @return
	 */
	public List<SystemFileInfo> selectByUuids(List<String> videoIdList);
}
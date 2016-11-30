package com.bluemobi.www.data.dao.system;

import java.util.List;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemPictureInfoDao extends BaseDao<SystemPictureInfo>{
	
	public String sqlNameSpace=SystemPictureInfoDao.class.getName();

	/**
	 * 根据uuid集合查询图片信息列表
	 * @param uuidList
	 * @return
	 */
	public List<SystemPictureInfo> selectByUuids(List<String> uuidList);
	
	public SystemPictureInfo selectEntityByUuid(String uuid);
}
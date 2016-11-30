package com.bluemobi.www.data.dao.system.impl;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemPictureInfoDao;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemPictureInfoDaoImpl extends BaseDaoImpl<SystemPictureInfo> implements SystemPictureInfoDao{
	
	public SystemPictureInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<SystemPictureInfo> selectByUuids(List<String> imageIdList) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByUuids", imageIdList);
	}
	
	@Override
	public SystemPictureInfo selectEntityByUuid(String uuid) {
		return dao.getSqlSessionTemplate().selectOne(sql_name_space + ".selectEntityByUuid", uuid);
	}
}
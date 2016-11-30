package com.bluemobi.www.data.dao.system.impl;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemFileInfoDao;
import com.bluemobi.www.data.model.system.SystemFileInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemFileInfoDaoImpl extends BaseDaoImpl<SystemFileInfo> implements SystemFileInfoDao{
	
	public SystemFileInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<SystemFileInfo> selectByUuids(List<String> videoIdList) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByUuids", videoIdList);
	}
}
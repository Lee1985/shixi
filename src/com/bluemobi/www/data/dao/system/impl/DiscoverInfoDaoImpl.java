package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.DiscoverInfoDao;
import com.bluemobi.www.data.model.system.DiscoverInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class DiscoverInfoDaoImpl extends BaseDaoImpl<DiscoverInfo> implements DiscoverInfoDao{
	public DiscoverInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
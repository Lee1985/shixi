package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.ConfigInfoDao;
import com.bluemobi.www.data.model.system.ConfigInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ConfigInfoDaoImpl extends BaseDaoImpl<ConfigInfo> implements ConfigInfoDao{
	public ConfigInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
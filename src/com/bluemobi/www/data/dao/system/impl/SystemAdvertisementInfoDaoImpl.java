package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemAdvertisementInfoDao;
import com.bluemobi.www.data.model.system.SystemAdvertisementInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemAdvertisementInfoDaoImpl extends BaseDaoImpl<SystemAdvertisementInfo> implements SystemAdvertisementInfoDao{
	public SystemAdvertisementInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
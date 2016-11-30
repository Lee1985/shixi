package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemHotspotCityDao;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemHotspotCityDaoImpl extends BaseDaoImpl<SystemHotspotCity> implements SystemHotspotCityDao{
	public SystemHotspotCityDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
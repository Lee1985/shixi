package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberActionsInfoDao;
import com.bluemobi.www.data.model.member.MemberActionsInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberActionsInfoDaoImpl extends BaseDaoImpl<MemberActionsInfo> implements MemberActionsInfoDao{
	public MemberActionsInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
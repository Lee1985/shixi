package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberFollowInfoDao;
import com.bluemobi.www.data.model.member.MemberFollowInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberFollowInfoDaoImpl extends BaseDaoImpl<MemberFollowInfo> implements MemberFollowInfoDao{
	public MemberFollowInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberFollowInfo;
/**
 * 数据访问接口
 *
 */
public interface MemberFollowInfoDao extends BaseDao<MemberFollowInfo>{
	public String sqlNameSpace=MemberFollowInfoDao.class.getName();
}
package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberActionsInfo;
/**
 * 数据访问接口
 *
 */
public interface MemberActionsInfoDao extends BaseDao<MemberActionsInfo>{
	public String sqlNameSpace=MemberActionsInfoDao.class.getName();
}
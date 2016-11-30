package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberResumeApply;
/**
 * 数据访问接口
 *
 */
public interface MemberResumeApplyDao extends BaseDao<MemberResumeApply>{
	public String sqlNameSpace=MemberResumeApplyDao.class.getName();
}
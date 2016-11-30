package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberResumeInfo;
/**
 * 数据访问接口
 *
 */
public interface MemberResumeInfoDao extends BaseDao<MemberResumeInfo>{
	public String sqlNameSpace=MemberResumeInfoDao.class.getName();
}
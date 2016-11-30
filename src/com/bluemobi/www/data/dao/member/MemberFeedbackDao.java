package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberFeedback;
/**
 * 数据访问接口
 *
 */
public interface MemberFeedbackDao extends BaseDao<MemberFeedback>{
	public String sqlNameSpace=MemberFeedbackDao.class.getName();
}
package com.bluemobi.www.data.dao.message;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.message.MessageOfficialNotice;
/**
 * 数据访问接口
 *
 */
public interface MessageOfficialNoticeDao extends BaseDao<MessageOfficialNotice>{
	public String sqlNameSpace=MessageOfficialNoticeDao.class.getName();
}
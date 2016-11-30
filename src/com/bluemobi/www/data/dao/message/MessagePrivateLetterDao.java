package com.bluemobi.www.data.dao.message;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.message.MessagePrivateLetter;
import com.bluemobi.www.page.PageInfo;
/**
 * 数据访问接口
 *
 */
public interface MessagePrivateLetterDao extends BaseDao<MessagePrivateLetter>{
	
	public String sqlNameSpace=MessagePrivateLetterDao.class.getName();

	List<Map<String,Object>> selectUnreadInfo(Map<String, Object> unreadParams);
	
	PageInfo<MessagePrivateLetter> selectPrivateMessageRecord(MessagePrivateLetter info, PageInfo<MessagePrivateLetter> pageInfo);
	
	
}
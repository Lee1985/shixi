package com.bluemobi.www.data.dao.message;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.page.PageInfo;
/**
 * 数据访问接口
 *
 */
public interface MessagePrivateMessageRecentDao extends BaseDao<MessagePrivateMessageRecent>{
	
	public String sqlNameSpace=MessagePrivateMessageRecentDao.class.getName();
	
	/**
	 * 查询最近联系人私信类型(官方、私人)
	 * @param params
	 * @return
	 */
	public List<MessagePrivateMessageRecent> selectByType(Map<String,Object> params);

	/**
	 * 后台私信列表
	 * @param info
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<MessagePrivateMessageRecent> selectPrivateMessageRecord(MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo);
	
	/**
	 * 后台查询联系人
	 * @param info
	 * @param pageInfo
	 * @return
	 */
	public PageInfo<MessagePrivateMessageRecent> selectRecentContactRecord(MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo);
	
}
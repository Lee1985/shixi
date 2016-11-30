package com.bluemobi.www.security.message.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface MessagePrivateMessageRecentService extends BaseService<MessagePrivateMessageRecent>{
	
	/**
	 * 查询最近联系人私信类型(官方、私人)
	 * @param params
	 * @return
	 */
	public List<MessagePrivateMessageRecent> selectByType(Map<String, Object> params);

	/**
	 * 查询私信最近联系人
	 * @param messageRecentParams
	 * @param recentPage
	 * @return
	 */
	public PageInfo<MessagePrivateMessageRecent> selectByType(Map<String, Object> messageRecentParams,PageInfo<MessagePrivateMessageRecent> recentPage);
	
	
	/**
	 * 后台私信列表
	 * @param info
	 * @param pageInfo
	 * @return
	 */
	PageInfo<MessagePrivateMessageRecent> selectPrivateMessageRecord(MessagePrivateMessageRecent info, PageInfo<MessagePrivateMessageRecent> pageInfo);
	
	/**
	 * 后台用户联系人列表
	 * @param info
	 * @param pageInfo
	 * @return
	 */
	PageInfo<MessagePrivateMessageRecent> selectRecentContactRecord(MessagePrivateMessageRecent info, PageInfo<MessagePrivateMessageRecent> pageInfo);
	

}

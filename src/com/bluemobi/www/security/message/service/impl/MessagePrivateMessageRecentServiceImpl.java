package com.bluemobi.www.security.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.message.MessagePrivateMessageRecentDao;
import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.message.service.MessagePrivateMessageRecentService;

@Service
public class MessagePrivateMessageRecentServiceImpl extends BaseServiceImpl<MessagePrivateMessageRecent> implements MessagePrivateMessageRecentService{

	@Autowired
	private MessagePrivateMessageRecentDao messagePrivateMessageRecentDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(messagePrivateMessageRecentDao);
	}

	@Override
	public List<MessagePrivateMessageRecent> selectByType(Map<String, Object> params) {
		return messagePrivateMessageRecentDao.selectByType(params);
	}

	@Override
	public PageInfo<MessagePrivateMessageRecent> selectByType(Map<String, Object> messageRecentParams,PageInfo<MessagePrivateMessageRecent> recentPage) {
		MessagePrivateMessageRecent privateMessageRecent = new MessagePrivateMessageRecent();	
		privateMessageRecent.setMemberId(messageRecentParams.get("memberId").toString());
		privateMessageRecent.setType(messageRecentParams.get("identityType").toString());
		privateMessageRecent.setSort(messageRecentParams.get("sort").toString());
		privateMessageRecent.setOrder(messageRecentParams.get("order").toString());
		return selectAll(privateMessageRecent, recentPage, "selectByType");
	}
	
	@Override
	public PageInfo<MessagePrivateMessageRecent> selectPrivateMessageRecord(MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo) {
		return messagePrivateMessageRecentDao.selectPrivateMessageRecord(info,pageInfo);
	}
	
	@Override
	public PageInfo<MessagePrivateMessageRecent> selectRecentContactRecord(
			MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo) {
		return messagePrivateMessageRecentDao.selectRecentContactRecord(info,pageInfo);
	}
	
	
	
}
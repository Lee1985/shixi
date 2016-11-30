package com.bluemobi.www.security.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.message.MessagePrivateLetterDao;
import com.bluemobi.www.data.model.message.MessagePrivateLetter;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.message.service.MessagePrivateLetterService;

@Service
public class MessagePrivateLetterServiceImpl extends BaseServiceImpl<MessagePrivateLetter> implements MessagePrivateLetterService{

	@Autowired
	private MessagePrivateLetterDao messagePrivateLetterDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(messagePrivateLetterDao);
	}

	@Override
	public PageInfo<MessagePrivateLetter> selectPrivateRecords(Map<String, Object> params,PageInfo<MessagePrivateLetter> letterPage) {		
		MessagePrivateLetter messagePrivateLetter = new MessagePrivateLetter();		
		messagePrivateLetter.setMemberId(params.get("memberId").toString());
		messagePrivateLetter.setContactId(params.get("contactId").toString());
		return selectAll(messagePrivateLetter, letterPage, "selectPrivateRecords");		
	}

	@Override
	public List<Map<String,Object>> selectUnreadInfo(Map<String, Object> unreadParams) {
		return messagePrivateLetterDao.selectUnreadInfo(unreadParams);
	}	
	
}
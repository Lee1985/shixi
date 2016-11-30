package com.bluemobi.www.security.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.message.MessageOfficialNoticeDao;
import com.bluemobi.www.data.model.message.MessageOfficialNotice;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.message.service.MessageOfficialNoticeService;

@Service
public class MessageOfficialNoticeServiceImpl extends BaseServiceImpl<MessageOfficialNotice> implements MessageOfficialNoticeService{

	@Autowired
	private MessageOfficialNoticeDao messageOfficialNoticeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(messageOfficialNoticeDao);
	}
	
}
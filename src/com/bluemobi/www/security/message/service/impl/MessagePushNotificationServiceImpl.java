package com.bluemobi.www.security.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.message.MessagePushNotificationDao;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.utils.PushUtils;

@Service
public class MessagePushNotificationServiceImpl extends BaseServiceImpl<MessagePushNotification> implements MessagePushNotificationService{

	@Autowired
	private MessagePushNotificationDao messagePushNotificationDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(messagePushNotificationDao);
	}
	
	@Override
	public int insert(MessagePushNotification info){
		int munber = messagePushNotificationDao.insert(info);
		if(info.getReceiveId() != null){
			try {
				PushUtils.pushToSingleIos(info.getCid(), info.getTitle(), info.getContent(), "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return munber;
	}
}
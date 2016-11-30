package com.bluemobi.www.data.dao.message;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.message.MessagePushNotification;
/**
 * 数据访问接口
 *
 */
public interface MessagePushNotificationDao extends BaseDao<MessagePushNotification>{
	public String sqlNameSpace=MessagePushNotificationDao.class.getName();
}
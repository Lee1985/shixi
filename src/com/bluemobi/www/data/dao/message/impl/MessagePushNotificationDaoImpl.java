package com.bluemobi.www.data.dao.message.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.message.MessagePushNotificationDao;
import com.bluemobi.www.data.model.message.MessagePushNotification;
/**
 * 
 * @author mew
 *
 */
@Component
public class MessagePushNotificationDaoImpl extends BaseDaoImpl<MessagePushNotification> implements MessagePushNotificationDao{
	public MessagePushNotificationDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}
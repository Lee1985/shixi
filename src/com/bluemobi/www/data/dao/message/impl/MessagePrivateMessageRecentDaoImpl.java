package com.bluemobi.www.data.dao.message.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.message.MessagePrivateMessageRecentDao;
import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.page.PageInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
/**
 * 
 * @author mew
 *
 */
@Component
public class MessagePrivateMessageRecentDaoImpl extends BaseDaoImpl<MessagePrivateMessageRecent> implements MessagePrivateMessageRecentDao{
	public MessagePrivateMessageRecentDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<MessagePrivateMessageRecent> selectByType(Map<String, Object> params) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByType", params);
	}

	@Override
	public PageInfo<MessagePrivateMessageRecent> selectPrivateMessageRecord(MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo) {
		try {
			int page = pageInfo.getPage();
			int pageSize = pageInfo.getPageSize();
			if (page<=0) {
				page=1;
			}
			PageBounds pageBounds = new PageBounds(page, pageSize);
			List<MessagePrivateMessageRecent> list = dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"selectPrivateMessageRecord", info, pageBounds);
			PageList<MessagePrivateMessageRecent> results = (PageList<MessagePrivateMessageRecent>)list;
			pageInfo.setRows(results);
			Paginator paginator = results.getPaginator();
			pageInfo.setTotal(paginator.getTotalCount());
			pageInfo.initPages();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	@Override
	public PageInfo<MessagePrivateMessageRecent> selectRecentContactRecord(MessagePrivateMessageRecent info,PageInfo<MessagePrivateMessageRecent> pageInfo) {
		try {
			int page = pageInfo.getPage();
			int pageSize = pageInfo.getPageSize();
			if (page<=0) {
				page=1;
			}
			PageBounds pageBounds = new PageBounds(page, pageSize);
			List<MessagePrivateMessageRecent> list = dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"selectRecentContactRecord", info, pageBounds);
			PageList<MessagePrivateMessageRecent> results = (PageList<MessagePrivateMessageRecent>)list;
			pageInfo.setRows(results);
			Paginator paginator = results.getPaginator();
			pageInfo.setTotal(paginator.getTotalCount());
			pageInfo.initPages();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
}
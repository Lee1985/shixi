package com.bluemobi.www.data.dao.message.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.message.MessagePrivateLetterDao;
import com.bluemobi.www.data.model.message.MessagePrivateLetter;
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
public class MessagePrivateLetterDaoImpl extends BaseDaoImpl<MessagePrivateLetter> implements MessagePrivateLetterDao{
	public MessagePrivateLetterDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
	
	@Override
	public List<Map<String,Object>> selectUnreadInfo(Map<String, Object> unreadParams) {
		return dao.getSqlSessionTemplate().selectList("selectUnreadInfo", unreadParams);
	}

	@Override
	public PageInfo<MessagePrivateLetter> selectPrivateMessageRecord(MessagePrivateLetter info, PageInfo<MessagePrivateLetter> pageInfo) {						
		try {
			int page = pageInfo.getPage();
			int pageSize = pageInfo.getPageSize();
			if (page<=0) {
				page=1;
			}
			PageBounds pageBounds = new PageBounds(page, pageSize);
			List<MessagePrivateLetter> list = dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"selectPrivateMessageRecord", info, pageBounds);
			PageList<MessagePrivateLetter> results = (PageList<MessagePrivateLetter>)list;
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
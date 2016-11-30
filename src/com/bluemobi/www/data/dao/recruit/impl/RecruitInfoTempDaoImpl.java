package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitInfoTempDao;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
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
public class RecruitInfoTempDaoImpl extends BaseDaoImpl<RecruitInfoTemp> implements RecruitInfoTempDao{
	public RecruitInfoTempDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public PageInfo<RecruitInfoTemp> selectAllByStatus(RecruitInfoTemp info,
			PageInfo<RecruitInfoTemp> pageInfo) {
		try {
			PageList<RecruitInfoTemp> results = selectAllByStatus(info,pageInfo.getPage(), pageInfo.getPageSize());
			pageInfo.setRows(results);
			Paginator paginator = results.getPaginator();
			pageInfo.setTotal(paginator.getTotalCount());
			pageInfo.initPages();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	public PageList selectAllByStatus(RecruitInfoTemp info,
			int page, int pageSize){
		if (page<=0) {
			page=1;
		}
		PageBounds pageBounds = new PageBounds(page, pageSize);
		PageList result = (PageList) dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"selectAllByStatus", info, pageBounds);
		return result;
	}
}
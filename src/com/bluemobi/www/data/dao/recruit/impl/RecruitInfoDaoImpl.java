package com.bluemobi.www.data.dao.recruit.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
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
public class RecruitInfoDaoImpl extends BaseDaoImpl<RecruitInfo> implements RecruitInfoDao{
	public RecruitInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public PageInfo<RecruitInfo> selectInfoAll(RecruitInfo info, PageInfo<RecruitInfo> pageInfo) {
		PageList<RecruitInfo> results = selectInfoAll(info,pageInfo.getPage(), pageInfo.getPageSize());
		pageInfo.setRows(results);
		Paginator paginator = results.getPaginator();
		pageInfo.setTotal(paginator.getTotalCount());
		pageInfo.initPages();
		
		return pageInfo;
	}
	@Override
	public PageList<RecruitInfo> selectInfoAll(RecruitInfo info, int page, int pageSize) {
		if (page<=0) {
			page=1;
		}
		PageBounds pageBounds = new PageBounds(page, pageSize);
		PageList result = (PageList) dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"selectInfoAll", info, pageBounds);
		return result;
	}

	@Override
	public void addViewNum(RecruitInfo recruitInfo) {
		dao.getSqlSessionTemplate().update(sql_name_space + ".updateViewNum",
				recruitInfo);
	}

	@Override
	public PageInfo<RecruitInfo> batchSelectByMemberId(
			Map<String, Object> info, PageInfo<RecruitInfo> pageInfo) {
		PageList<RecruitInfo> results = batchSelectByMemberId(info , pageInfo.getPage(), pageInfo.getPageSize());
		pageInfo.setRows(results);
		Paginator paginator = results.getPaginator();
		pageInfo.setTotal(paginator.getTotalCount());
		pageInfo.initPages();
		
		return pageInfo;
	}
	
	@Override
	public PageList<RecruitInfo> batchSelectByMemberId(Map<String, Object> info, int page, int pageSize) {
		if (page<=0) {
			page=1;
		}
		PageBounds pageBounds = new PageBounds(page, pageSize);
		PageList result = (PageList) dao.getSqlSessionTemplate().selectList(sql_name_space+"."+"batchSelectByMemberId", info, pageBounds);
		return result;
	}

	@Override
	public int batchUpdateByMemberId(Map<String, Object> info) {
		return dao.getSqlSessionTemplate().delete(sql_name_space + ".batchUpdateByMemberId",info);
	}
}
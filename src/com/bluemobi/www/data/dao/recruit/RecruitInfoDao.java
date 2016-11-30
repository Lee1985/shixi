package com.bluemobi.www.data.dao.recruit;


import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.page.PageInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
/**
 * 数据访问接口
 *
 */
public interface RecruitInfoDao extends BaseDao<RecruitInfo>{
	public String sqlNameSpace=RecruitInfoDao.class.getName();
	
	public PageInfo<RecruitInfo> selectInfoAll(RecruitInfo info, PageInfo<RecruitInfo> pageInfo);
	
	public PageList<RecruitInfo> selectInfoAll(RecruitInfo info, int page, int pageSize);
	
	public void addViewNum(RecruitInfo recruitInfo);
	
	public PageInfo<RecruitInfo> batchSelectByMemberId(Map<String, Object> info, PageInfo<RecruitInfo> pageInfo);

	public PageList<RecruitInfo> batchSelectByMemberId(Map<String, Object> info, int page, int pageSize);
	
	public int batchUpdateByMemberId(Map<String, Object> info);
}
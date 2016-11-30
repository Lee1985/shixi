package com.bluemobi.www.security.base.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.page.PageInfo;

public interface BaseService<T> {
	public List<T> selectAll();

	public List<T> selectAll(T info);
	
	public List<T> selectAll(T info,String statementName);
	
	public List<T> selectAll(Map<String, Object> info,String statementName);

	public List<T> selectAll(Map<String, Object> info);

	public List<T> selectAll(T info,int page,int pageSize);
	
	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @param statementName SqlMapperId
	 * @return
	 */
	public List<T> selectAll(T info, int page, int pageSize, String statementName);

	public List<T> selectAll(Map<String, Object> info,int page,int pageSize);
	
	public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo);
	
	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param pageInfo分页对象
	 * @param statementName SqlMapperId
	 * @return
	 */
	public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo, String statementName);

	public PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo);

	public int selectCount(T info);
	
	public int selectCount(Map<String, Object> info);

	public T selectById(String id);

	public T selectById(Integer id);

	public int insert(T info);

	public int insert(Map<String, Object> info);

	public int update(T info);

	public int update(Map<String, Object> info);
	
	public int batchUpdate(Map<String, Object> info);

	public int delete(T info);

	public int delete(Map<String, Object> info);
	
	public int batchDelete(List<String> idList);
	
	public T selectEntity(T info);

	public T selectEntity(Map<String, Object> info);
	
	public List<T> selectByIds(List<String> idList);
}

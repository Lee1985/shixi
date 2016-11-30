package com.bluemobi.www.data.dao;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.page.PageInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface BaseDao<T> {
	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<T> selectAll();

	/**
	 * 根据条件查询数据
	 * 
	 * @param info参数对象
	 * @return
	 */
	public List<T> selectAll(T info);
	
	/**
	 * 根据条件自定义查询数据
	 * 
	 * @param info参数对象
	 * @return
	 */
	public List<T> selectAll(T info,String statementName);
	
	/**
	 * 根据条件自定义查询数据
	 * 
	 * @param info参数对象
	 * @return
	 */
	public List<T> selectAll(Map<String, Object> info,String statementName);


	/**
	 * 根据条件查询数据
	 * 
	 * @param info参数对象
	 * @return
	 */
	public List<T> selectAll(Map<String, Object> info);
	
	/**
	 * 根据外键查询信息
	 * @param idList
	 * @return
	 */
	public List<T> selectInfoByKey(List<String> idList,String statement);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public List<T> selectAll(T info, int page, int pageSize);
	
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
	

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public List<T> selectAll(Map<String, Object> info, int page, int pageSize);
	
	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectAllMap();

	/**
	 * 根据条件查询数据
	 * 
	 * @param info
	 * @return
	 */
	public List<Map<String, Object>> selectAllMap(T info);

	/**
	 * 根据条件查询数据
	 * 
	 * @param info
	 * @return
	 */
	public List<Map<String, Object>> selectAllMap(Map<String, Object> info);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件
	 * @param page页码
	 * @param pageSize数量
	 * @return
	 */
	public List<Map<String, Object>> selectAllMap(T info, int page, int pageSize);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件
	 * @param page页码
	 * @param pageSize数量
	 * @return
	 */
	public List<Map<String, Object>> selectAllMap(Map<String, Object> info,
			int page, int pageSize);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param pageInfo分页对象
	 * @return
	 */
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
	
	

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param pageInfo分页对象
	 * @return
	 */
	public PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo);
	

	/**
	 * 根据条件查询行数
	 * 
	 * @param info
	 * @return
	 */
	public int selectCount(T info);

	/**
	 * 根据条件查询行数
	 * 
	 * @param info
	 * @return
	 */
	public int selectCount(Map<String, Object> info);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	public T selectById(String id);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	public T selectById(Integer id);

	/**
	 * 根据条件查询对象
	 * 
	 * @param info
	 * @return
	 */
	public T selectEntity(T info);

	/**
	 * 根据条件查询对象
	 * 
	 * @param info
	 * @return
	 */
	public T selectEntity(Map<String, Object> info);

	/**
	 * 根据id查询对象map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectByIdMap(String id);

	/**
	 * 根据id查询对象map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectByIdMap(Integer id);

	/**
	 * 根据条件查询对象map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectEntityMap(T info);

	/**
	 * 根据条件查询对象map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectEntityMap(Map<String, Object> info);

	/**
	 * 插入实体
	 * 
	 * @param info
	 * @return
	 */
	public int insert(T info);

	/**
	 * 插入实体
	 * 
	 * @param info
	 * @return
	 */
	public int insert(Map<String, Object> info);

	/**
	 * 根据id更新实体
	 * 
	 * @param info
	 * @return
	 */
	public int update(T info);

	/**
	 * 根据id更新实体
	 * 
	 * @param info
	 * @return
	 */
	public int update(Map<String, Object> info);
	
	
	/**
	 * 根据id更新实体
	 * 
	 * @param info
	 * @return
	 */
	public int batchUpdate(Map<String, Object> info);
		
	/**
	 * 根据条件删除实体
	 * 
	 * @param info
	 * @return
	 */
	public int delete(T info);

	/**
	 * 根据条件删除实体
	 * 
	 * @param info
	 * @return
	 */
	public int delete(Map<String, Object> info);
	
	/**
	 * 根据主键ID做批量删除
	 * 
	 * @param info
	 * @return
	 */
	public int batchDelete(List<String> idList);
	
	/**
	 * 根据外做批量删除
	 * 
	 * @param info
	 * @return
	 */
	public int batchDeleteByForeignKey(List<String> idList,String statement);
	
	/**
	 * 根据外键删除实体
	 * @return
	 */
	public int deleteByForeignKey(String key,String statement);
	
	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public PageList<T> selectAll(String mappingId,T info, int page, int pageSize);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public PageList<T> selectAll(String mappingId,Map<String, Object> info, int page, int pageSize);
	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public PageList<Map<String, Object>> selectAllMap(String mappingId,T info, int page, int pageSize);

	/**
	 * 根据条件查询分页数据
	 * 
	 * @param info条件参数
	 * @param page页码
	 * @param pageSize条数
	 * @return
	 */
	public PageList<Map<String, Object>> selectAllMap(String mappingId,Map<String, Object> info, int page, int pageSize);

	public List<T> selectByIds(List<String> idList);


}

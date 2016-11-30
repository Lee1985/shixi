package com.bluemobi.www.security.base.service.impl;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.BaseService;

public class BaseServiceImpl<T> implements  BaseService<T>{
	
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
	    this.baseDao = baseDao;
	}

	@Override
	public List<T> selectAll() {
		return baseDao.selectAll();
	}

	@Override
	public List<T> selectAll(T info) {
		return baseDao.selectAll(info);
	}
	
	@Override
	public List<T> selectAll(T info, String statementName) {
		return baseDao.selectAll(info, statementName);
	}
	
	@Override
	public List<T> selectAll(Map<String, Object> info, String statementName) {
		return baseDao.selectAll(info, statementName);
	}

	@Override
	public List<T> selectAll(Map<String, Object> info) {
		return baseDao.selectAll(info);
	}

	@Override
	public List<T> selectAll(T info, int page, int pageSize) {
		return baseDao.selectAll(info,page,pageSize);
	}

	@Override
	public List<T> selectAll(Map<String, Object> info, int page, int pageSize) {
		return baseDao.selectAll(info,page,pageSize);
	}

	@Override
	public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo) {
		return baseDao.selectAll(info,pageInfo);
	}
	
	@Override
	public List<T> selectAll(T info, int page, int pageSize, String statementName) {
		return baseDao.selectAll(info, page, pageSize, statementName);
	}
	
	@Override
	public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo, String statementName) {
		return baseDao.selectAll(info, pageInfo, statementName);
	}

	@Override
	public PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo) {
		return baseDao.selectAll(info,pageInfo);
	}

	@Override
	public int selectCount(T info) {
		return baseDao.selectCount(info);
	}

	@Override
	public int selectCount(Map<String, Object> info) {
		return baseDao.selectCount(info);
	}

	@Override
	public T selectById(String id) {
		return baseDao.selectById(id);
	}

	@Override
	public T selectById(Integer id) {
		return baseDao.selectById(id);
	}

	@Override
	public int insert(T info) {
		return baseDao.insert(info);
	}

	@Override
	public int insert(Map<String, Object> info) {
		return baseDao.insert(info);
	}

	@Override
	public int update(T info) {
		return baseDao.update(info);
	}

	@Override
	public int update(Map<String, Object> info) {
		return baseDao.update(info);
	}

	@Override
	public int delete(T info) {
		return baseDao.delete(info);
	}

	@Override
	public int delete(Map<String, Object> info) {
		return baseDao.delete(info);
	}

	@Override
	public T selectEntity(T info) {
		return baseDao.selectEntity(info);
	}

	@Override
	public T selectEntity(Map<String, Object> info) {
		return baseDao.selectEntity(info);
	}

	@Override
	public int batchDelete(List<String> idList) {
		return baseDao.batchDelete(idList);
	}

	@Override
	public int batchUpdate(Map<String, Object> info) {
		return baseDao.batchUpdate(info);
	}

	@Override
	public List<T> selectByIds(List<String> idList) {
		return baseDao.selectByIds(idList);
	}

	
}

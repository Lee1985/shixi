package com.bluemobi.www.data;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import com.bluemobi.www.page.Page;

public interface DaoSupport {
	public int insert(String statement, Object parameter);

	public int update(String statement, Object parameter);

	public <K, V, T> T get(String statement, Map<K, V> parameter);

	public <K, V> Map<K, V> findOne(String statement, Map<K, V> parameter);
	
	public <E , K , V> E findEntityById(String statement , Map<K , V> parameter);
	
	public <E, K, V> List<E> find(String statement, Map<K, V> parameter);
	
	public <E> List<E> find(String statement);
	
	@SuppressWarnings("rawtypes")
	public <E> List<E> find(String statement , List objs);
	
	public <K , V> int getTotal(String statement , Map<K , V> parameter);

	public <K, V> int delete(String statement, Map<K, V> parameter);


	public <E, K, V> Page<E> page(String pageStatement, Map<K, V> parameter, int current, int pagesize);

	public Connection getConnection();

	public Configuration getConfiguration();

	public SqlSessionTemplate getSqlSessionTemplate();
}

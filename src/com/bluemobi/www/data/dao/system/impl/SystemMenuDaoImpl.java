package com.bluemobi.www.data.dao.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemMenuDao;
import com.bluemobi.www.data.model.system.SystemMenu;
@Component
public class SystemMenuDaoImpl extends BaseDaoImpl<SystemMenu> implements SystemMenuDao{
	public SystemMenuDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<SystemMenu> selectAllByPid(String pid) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectList(
				sql_name_space + ".selectAllByPid", pid);
	}

	@Override
	public List<SystemMenu> selectAllByPid(Integer pid) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectList(
				sql_name_space + ".selectAllByPid", pid);
	}

	@Override
	public int selectMaxOrderListByPid(SystemMenu info) {
		// TODO Auto-generated method stub
		int count = 0;
		SqlSession session = null;
		try {
			session = dao.getSqlSessionTemplate().getSqlSessionFactory()
					.openSession();
			Object obj= session.selectOne(sql_name_space + "."
					+ "selectMaxOrderListByPid", info);
			if (obj!=null) {
				count = (Integer)obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
		}
		return count;
	}

	@Override
	public int selectMaxOrderListByPid(Map<String, Object> info) {
		// TODO Auto-generated method stub
		int count = 0;
		SqlSession session = null;
		try {
			session = dao.getSqlSessionTemplate().getSqlSessionFactory()
					.openSession();
			count = (Integer) session.selectOne(sql_name_space + "."
					+ "selectMaxOrderListByPid", info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
		}
		return count;
	}

	@Override
	public int updateOrderListDown(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().update(sql_name_space + ".updateOrderListDown",
				info);
	}

	@Override
	public int updateOrderListUp(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().update(sql_name_space + ".updateOrderListUp",
				info);
	}

	@Override
	public List<SystemMenu> selectAllByRole(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectList(
				sql_name_space + ".selectAllByRole", info);
	}

	@Override
	public List<SystemMenu> selectAllByRoleLogin(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectList(
				sql_name_space + ".selectAllByRoleLogin", info);
	}
	
}

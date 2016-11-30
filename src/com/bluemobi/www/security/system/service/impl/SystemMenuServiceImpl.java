package com.bluemobi.www.security.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluemobi.www.data.dao.system.SystemMenuActDao;
import com.bluemobi.www.data.dao.system.SystemMenuActUrlDao;
import com.bluemobi.www.data.dao.system.SystemMenuDao;
import com.bluemobi.www.data.model.system.SystemMenu;
import com.bluemobi.www.data.model.system.SystemMenuAct;
import com.bluemobi.www.data.model.system.SystemMenuActUrl;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemMenuService;
import com.bluemobi.www.utils.UUIDUtil;

@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenu> implements SystemMenuService {
	@Autowired
	private SystemMenuDao dao;

	@Autowired
	private SystemMenuActDao systemMenuActDao;
	
	@Autowired
	private SystemMenuActUrlDao systemMenuActUrlDao;


	@Transactional
	@Override
	public int insert(SystemMenu info) {
		// TODO Auto-generated method stub
		SystemMenuAct systemMenuAct = new SystemMenuAct();
		systemMenuAct.setId(UUIDUtil.getUUID());
		systemMenuAct.setActCode("menu");
		systemMenuAct.setActName("菜单");
		systemMenuAct.setMenuId(info.getId());
		systemMenuAct.setPosition("0");
		systemMenuActDao.insert(systemMenuAct);
		return dao.insert(info);
	}

	@Override
	public int insert(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return dao.insert(info);
	}

	@Override
	public int update(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.update(info);
	}

	@Override
	public int update(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return dao.update(info);
	}

	@Transactional
	@Override
	public int delete(SystemMenu info) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			SystemMenu systemMenu = selectById(info.getId());
			List<String> ids = new ArrayList<String>();
			getIds(ids, systemMenu);
			SystemMenu condition = new SystemMenu();
			condition.setIds(ids);
			
			//删除资源
			SystemMenuActUrl systemMenuActUrl=new SystemMenuActUrl();
			systemMenuActUrl.setMenuIds(ids);
			systemMenuActUrlDao.delete(systemMenuActUrl);
			//删除动作
			SystemMenuAct systemMenuAct=new SystemMenuAct();
			systemMenuAct.setMenuIds(ids);
			systemMenuActDao.delete(systemMenuAct);
			//删除菜单
			result = dao.delete(condition);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public List<SystemMenu> selectAllByPid(String pid) {
		// TODO Auto-generated method stub
		return dao.selectAllByPid(pid);
	}

	@Override
	public List<SystemMenu> selectAllByPid(Integer pid) {
		// TODO Auto-generated method stub
		return dao.selectAllByPid(pid);
	}


	@Override
	public int systemMenuDrag(String id, String pid, String moveType) {
		// TODO Auto-generated method stub
		int result = 0;
		System.out.println("id:" + id);
		System.out.println("pid:" + pid);
		System.out.println("moveType:" + moveType);
		SystemMenu menu = new SystemMenu();
		menu.setId(id);
		if (moveType.equals("inner")) {
			if (pid.equals("root")) {
				menu.setPid("0");
			} else {
				menu.setPid(pid);
			}
			SystemMenu own = dao.selectById(id);
			List<SystemMenu> menus = dao.selectAllByPid(own.getPid());
			List<String> ids = getListIds(menus);
			own.setIds(ids);
			dao.updateOrderListUp(own);

			int max = dao.selectMaxOrderListByPid(menu);
			menu.setOrderList(max + 1);
			result = dao.update(menu);
		}
		// target下方
		if (moveType.equals("next")) {
			SystemMenu own = null;
			SystemMenu target = null;
			List<String> ids = null;
			List<SystemMenu> menus = null;
			int ownOrderList = 0;
			int targetOrderList = 0;
			target = dao.selectById(pid);
			own = dao.selectById(id);
			ownOrderList = own.getOrderList();
			targetOrderList = target.getOrderList();
			if (target.getPid().equals(own.getPid())) {
				// 同一级
				menus = dao.selectAllByPid(own.getPid());
				ids = getListIds(menus);
				// own在target下方
				if (own.getOrderList() > target.getOrderList()) {
					// 先上移在下移
					// 上移
					own.setIds(ids);
					dao.updateOrderListUp(own);
					// 下移
					target.setIds(ids);
					dao.updateOrderListDown(target);
					// 更新own
					own.setOrderList(target.getOrderList() + 1);
					dao.update(own);
				} else {
					// 先下移在上移
					// 下移
					target.setIds(ids);
					dao.updateOrderListDown(target);
					// 更新own
					own.setOrderList(target.getOrderList() + 1);
					dao.update(own);
					// 上移
					own.setOrderList(ownOrderList);
					own.setIds(ids);
					dao.updateOrderListUp(own);
				}
			} else {
				// 不同级
				// 下移
				menus = dao.selectAllByPid(target.getPid());
				ids = getListIds(menus);
				target.setIds(ids);
				String ownPid = own.getPid();
				dao.updateOrderListDown(target);
				// 更新own
				own.setOrderList(target.getOrderList() + 1);
				own.setPid(target.getPid());
				dao.update(own);
				// 上移
				own.setPid(ownPid);
				menus = dao.selectAllByPid(own.getPid());
				ids = getListIds(menus);
				own.setIds(ids);
				own.setOrderList(ownOrderList);
				dao.updateOrderListUp(own);
			}

		}
		if (moveType.equals("prev")) {
			SystemMenu own = null;
			SystemMenu target = null;
			List<String> ids = null;
			List<SystemMenu> menus = null;
			target = dao.selectById(pid);
			own = dao.selectById(id);
			int ownOrderList = 0;
			int targetOrderList = 0;
			ownOrderList = own.getOrderList();
			targetOrderList = target.getOrderList();
			if (target.getPid().equals(own.getPid())) {
				// 同一级
				menus = dao.selectAllByPid(own.getPid());
				ids = getListIds(menus);
				// own在target下方
				if (own.getOrderList() > target.getOrderList()) {
					// 先上移在下移
					// 上移
					own.setIds(ids);
					dao.updateOrderListUp(own);
					// 下移
					target.setIds(ids);
					target.setOrderList(target.getOrderList() - 1);
					dao.updateOrderListDown(target);
					// 更新own
					own.setOrderList(targetOrderList);
					dao.update(own);
				} else {
					// 先下移在上移
					// 下移
					target.setIds(ids);
					target.setOrderList(target.getOrderList() - 1);
					dao.updateOrderListDown(target);
					// 更新own
					own.setOrderList(targetOrderList);
					dao.update(own);
					// 上移
					own.setIds(ids);
					own.setOrderList(ownOrderList);
					dao.updateOrderListUp(own);
				}
			} else {
				// 不同级
				// 下移
				String ownPid = own.getPid();
				menus = dao.selectAllByPid(target.getPid());
				ids = getListIds(menus);
				target.setIds(ids);
				target.setOrderList(target.getOrderList() - 1);
				dao.updateOrderListDown(target);
				// 更新own
				own.setOrderList(targetOrderList);
				own.setPid(target.getPid());
				dao.update(own);
				// 上移
				own.setPid(ownPid);
				menus = dao.selectAllByPid(own.getPid());
				ids = getListIds(menus);
				own.setIds(ids);
				own.setOrderList(ownOrderList);
				dao.updateOrderListUp(own);
			}
		}
		return result;
	}

	@Override
	public int selectMaxOrderListByPid(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.selectMaxOrderListByPid(info);
	}

	@Override
	public int selectMaxOrderListByPid(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return dao.selectMaxOrderListByPid(info);
	}

	private List<String> getIds(List<String> ids, SystemMenu menu) {
		ids.add(menu.getId());
		if (menu.getChildren() != null && menu.getChildren().size() > 0) {
			for (SystemMenu item : menu.getChildren()) {
				getIds(ids, item);
			}
		}

		return ids;
	}

	private List<String> getChildIds(SystemMenu menu) {
		List<String> ids = new ArrayList<String>();
		for (SystemMenu item : menu.getChildren()) {
			ids.add(item.getId());
		}
		return ids;
	}

	private List<String> getListIds(List<SystemMenu> menus) {
		List<String> ids = new ArrayList<String>();
		for (SystemMenu item : menus) {
			ids.add(item.getId());
		}
		return ids;
	}

	@Override
	public List<SystemMenu> selectAllByRole(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.selectAllByRole(info);
	}

	@Override
	public List<SystemMenu> selectAllByRoleLogin(SystemMenu info) {
		// TODO Auto-generated method stub
		return dao.selectAllByRoleLogin(info);
	}

}

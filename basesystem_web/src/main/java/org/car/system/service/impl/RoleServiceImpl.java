package org.car.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.car.common.model.JQTableVO;
import org.car.common.model.PageDTO;
import org.car.common.utils.ClassUtil;
import org.car.system.dao.IAuthorityDAO;
import org.car.system.dao.IRoleDAO;
import org.car.system.model.Role;
import org.car.system.model.RoleMenuAuthority;
import org.car.system.model.User;
import org.car.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
	private static final Log log = LogFactory.getLog(RoleServiceImpl.class); 

	@Autowired
	private IRoleDAO roleDAO;
	@Autowired
	private IAuthorityDAO menuAuthorityDAO;
	
	@Override
	public JQTableVO queryAllRoleForPage(PageDTO page, Role role) {
		JQTableVO vo = new JQTableVO(page);
		// TODO Auto-generated method stub
		Map<String,Object> params = ClassUtil.getObjDeclaredFields(page,role);
		int total = roleDAO.getAllRoleSize(params);
		List<Role> userList = roleDAO.getAllRoleForPage(params);
		vo.setDataList(userList);
		vo.setDataCount(total);
		log.info("查询管理员列表信息,查询条件");
		return vo;
	}

	@Override
	public int save(Role role) {
		// TODO Auto-generated method stub
		return roleDAO.insertRole(role);
	}
	@Override
	public int edit(Role role) {
		// TODO Auto-generated method stub
		role.setUpdateTime(new Date());
		return roleDAO.updateRole(role);
	}

	@Override
	public Role findRoleByRoleId(long id) {
		// TODO Auto-generated method stub
		return roleDAO.getRoleById(id);
	}


	@Override
	public int configureMenuAuthority(long roleId, String authority,long userId) {
		// TODO Auto-generated method stub
		List<RoleMenuAuthority> list = new ArrayList<RoleMenuAuthority>();
		if(StringUtils.isBlank(authority)){
			return 0;
		}
		//清除旧的数据
		menuAuthorityDAO.deleteRoleAuthorityByRoleId(roleId);
		String[] menuids = authority.split(",");
		for(String mid:menuids){
			RoleMenuAuthority r = new RoleMenuAuthority();
			r.setRoleId(roleId);
			r.setMenuId(Integer.parseInt(mid));
			r.setCreateUser(userId);
			list.add(r);
		}
		return menuAuthorityDAO.batchInsertRoleAuthority(list);
	}

	@Override
	public List<Role> queryAllRole() {
		// TODO Auto-generated method stub
		return roleDAO.getAllRole();
	}
}

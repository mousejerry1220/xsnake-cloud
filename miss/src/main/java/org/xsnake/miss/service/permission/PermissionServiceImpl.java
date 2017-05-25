package org.xsnake.miss.service.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.xsnake.dao.BaseDao;
import org.xsnake.miss.api.permission.IPermissionService;
import org.xsnake.miss.api.permission.Menu;
import org.xsnake.miss.api.permission.User;
import org.xsnake.miss.api.permission.entity.Employee;
import org.xsnake.miss.api.permission.entity.Org;
import org.xsnake.miss.api.permission.entity.Permission;
import org.xsnake.miss.api.permission.entity.Position;
import org.xsnake.miss.exception.MissException;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月19日 上午12:05:17 
* 
*/
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class PermissionServiceImpl implements IPermissionService{
	
	@Autowired
	BaseDao baseDao;
	
	public User login(String userCode, String password) {
		if(StringUtils.isEmpty(userCode ) || StringUtils.isEmpty(password)){
			throw new MissException("账号密码不能为空");
		}
		//查找用户
		Employee employee = baseDao.findUniqueEntity(" from Employee where code = ? ",new Object[]{userCode});
		if(employee == null){
			throw new MissException("账号不存在");
		}
		
		if(!password.equals(employee.getPassword())){
			throw new MissException("密码错误");
		}
		
		String positionId = employee.getLastPositionId() == null ? employee.getMainPositionId() : employee.getLastPositionId();
		return loadUserInfo(employee, positionId);
	}

	private User loadUserInfo(Employee employee, String positionId) {
		//加载岗位
		Position position = null;
		if(positionId == null){
			throw new MissException("用户未设置岗位");
		}
		position = baseDao.get(Position.class, positionId);
		if(position == null){
			throw new MissException("用户未设置岗位");
		}
		positionId = position.getId();
		
		//加载部门
		Org org = getOrgByPosition(positionId);
		
		//加载权限
		List<Permission> permissionList = findPermissionListByEmployee(employee.getId());
		
		//加载菜单
		List<Menu> menu = findMenuList(employee.getId());

		//记录用户最后登录的岗位
		employee.setLastPositionId(positionId);
		baseDao.update(employee);
		
		//构建用户
		User user = new User(org,position,employee,permissionList,menu);
		return user;
	}
	
	public Org getOrgByPosition(String positionId){
		StringBuffer hql = new StringBuffer();
		hql.append(" select org from TreeNode td , Org org where td.treeDefinitionId = 'ORG_POSITION' and td.nodeId = ? and td.parentId = org.id ");
		Org org = baseDao.findUniqueEntity(hql.toString(),positionId);
		return org;
	}
	
	public List<Menu> findMenuList(String employeeId){
		List<Permission> permissionMenuList = findPermissionListByEmployee(employeeId, true);

		List<Menu> result = new ArrayList<Menu>();
		List<Menu> menuList = new ArrayList<Menu>();
		
		Map<String,Menu> menuMap = new HashMap<String,Menu>();
		for(Permission permissionMenu : permissionMenuList){
			Menu menu = new Menu(permissionMenu);
			menuMap.put(permissionMenu.getId(), menu);
			menuList.add(menu);
		}
		
		for(Menu menu : menuList){
			if(menu.getParentId() !=null){
				Menu parentMenu = menuMap.get(menu.getParentId());
				if(parentMenu !=null){
					parentMenu.addChild(menu);
				}else{
					result.add(menu);
				}
			}
		}
		return result;
	}
	
	public Position getMainPositionByEmployee(String employeeId){
		StringBuffer hql = new StringBuffer();
		hql.append(" select p from ")
		   .append(" RelationEmployeePosition rep ,")
		   .append(" Position p ")
	   .append(" where p.id = rep.positionId ")
	   .append(" and rep.employeeId = ? ");
		Position position = baseDao.findUniqueEntity(hql.toString(),employeeId);
		return position;
	}
	
	public List<Position> findPositionByEmployee(String employeeId){
		StringBuffer hql = new StringBuffer();
		hql.append(" select p from ")
		   .append(" RelationEmployeePosition rep ,")
		   .append(" Position p ")
	   .append(" where p.id = rep.positionId ")
	   .append(" and rep.employeeId = ? ");
		List<Position> result = baseDao.findEntity(hql.toString(),employeeId);
		return result;
	}
	
	
	public List<Permission> findPermissionListByEmployee(String employeeId){
		return findPermissionListByEmployee(employeeId,false);
	}
	
	/**
	 * 通过员工获取到该用工的所有权限 
	 * @param employeeId
	 * @param isMenu
	 * @return
	 */
	private List<Permission> findPermissionListByEmployee(String employeeId,boolean isMenu){
		//岗位对应的权限
		StringBuffer hql = new StringBuffer();
		hql.append(" select p from ")
			   .append(" RelationEmployeePosition rep ,")
			   .append(" RelationPositionRole rpr ,")
			   .append(" RelationRolePermission rrp ,")
			   .append(" Permission p ")
		   .append(" where rep.employeeId = ? ")
		   .append(" and rep.positionId = rpr.positionId ")
		   .append(" and rpr.roleId = rrp.roleId ")
		   .append(" and rrp.permissionId = p.id ");
		if(isMenu){
			hql.append(" and p.type = '1' ");
		}
		List<Permission> employeePositionRolePermission = baseDao.findEntity(hql.toString(),employeeId);
		//角色对应的权限
		hql = new StringBuffer();
		hql.append(" select p from ")
			   .append(" RelationEmployeeRole rer ,")
			   .append(" RelationRolePermission rrp ,")
			   .append(" Permission p ")
		   .append(" where rer.employeeId = ? ")
		   .append(" and rer.roleId = rrp.roleId ")
		   .append(" and rrp.permissionId = p.id "); 
		if(isMenu){
			hql.append(" and p.type = '1' ");
		}	
		List<Permission> employeeRolePermission = baseDao.findEntity(hql.toString(),employeeId);
		//合并两者
		List<Permission> result = new ArrayList<Permission>();
		result.addAll(employeePositionRolePermission);
		result.addAll(employeeRolePermission);
		return result;
	}

	public User changePosition(String userId, String positionId) {
		Employee employee = baseDao.get(Employee.class, userId);
		if(employee == null){
			throw new MissException("用户不存在");
		}
		return loadUserInfo(employee, positionId);
	}

}

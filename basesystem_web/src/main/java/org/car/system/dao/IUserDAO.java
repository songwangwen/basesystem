package org.car.system.dao;

import java.util.List;
import java.util.Map;

import org.car.system.model.User;
import org.springframework.stereotype.Repository;

/**
 * 用户管理持久层接口，由spring完成实现
 * @author songwangwen
 */
@Repository
public interface IUserDAO {
	/**
	 * 查询所有用户资料
	 * @param params 分页及查询过滤条件
	 * @return List<User>
	 */
	public List<User> getAllUserForPage(Map<String, Object> params);
	/**
	 * 查询所有用户资料,带角色名称(逗号分隔)
	 * @param params 分页及查询过滤条件
	 * @return List<User>
	 */
//	public List<User> getAllUserForPageWithRole(Map<String,Object> params);
	
	/**
	 * 查询所有用户的总数，用于分页使用
	 * @param params 分页及查询过滤条件
	 * @return int 总记录数
	 */
	public int getAllUserCount(Map<String, Object> params);
	
	/**
	 * 根据用户名查询得到实体类对象，不存在则返回空
	 * @param userName 用户名
	 * @return List<User>
	 * @see User
	 */
	public List<User> getUserByUserName(String userName);
	
	/**
	 * 新增用户
	 * @param user 用户实体类
	 */
	public void insertUser(User user);
	/**
	 * 编辑用户
	 * @param user 用户实体类
	 */
	public int updateUserById(User user);
	/**
	 * 编辑用户
	 * @param user 修改密码
	 */
	public int updateUserPasswordById(User user);
	
	/**
	 * 根据用户ID查询得到用户，未查到则返回null
	 * @param userId
	 * @return User
	 * @see User
	 */
	public User getUserByUserId(long id);

}

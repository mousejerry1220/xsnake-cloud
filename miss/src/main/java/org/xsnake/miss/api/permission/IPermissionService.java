package org.xsnake.miss.api.permission;
/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月18日 下午5:31:49 
* 
*/
public interface IPermissionService {

	/**
	 * 登录
	 * @param userCode
	 * @param password
	 * @return
	 */
	User login(String userCode,String password);

	/**
	 * 切换岗位
	 * @param userId
	 * @param positionId
	 * @return
	 */
	User changePosition(String userId,String positionId);
	
}

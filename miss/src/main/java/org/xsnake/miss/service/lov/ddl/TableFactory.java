package org.xsnake.miss.service.lov.ddl;

import org.xsnake.dao.BaseDao;
import org.xsnake.miss.api.lov.model.LovGroupModel;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 下午11:36:39 
* 
*/
public abstract class TableFactory {

	public static final String TABLE_PREFIX = "T_LOV_";
	
	public static void createTable(String dialect,BaseDao baseDao,LovGroupModel lovGroupModel){
		TableFactory factory = null;
		if("ORACLE".equals(dialect)){
			factory = new OracleFactory();
		}else if("MYSQL".equals(dialect)){
			factory = new MysqlFactory();
		}else{
			throw new RuntimeException("unsupported");
		}
		factory.createTable(baseDao,lovGroupModel);
	}
	
	public abstract void createTable(BaseDao baseDao,LovGroupModel lovGroupModel);
}

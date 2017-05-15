package org.xsnake.miss.service.lov.ddl;

import java.util.List;

import org.xsnake.dao.BaseDao;
import org.xsnake.miss.api.lov.entity.LovGroup;
import org.xsnake.miss.api.lov.entity.LovGroupAttributes;
import org.xsnake.miss.api.lov.model.LovGroupModel;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月12日 下午11:51:23 
* 
*/

public class MysqlFactory extends TableFactory {
	
	@Override
	public void createTable(BaseDao baseDao,LovGroupModel lovGroupModel) {
		LovGroup lovGroup = lovGroupModel.getLovGroup();
		StringBuffer ddl = new StringBuffer();
		String tableName = "T_LOV_ATTR_"+lovGroup.getId();
		ddl.append("CREATE TABLE `").append(tableName).append("` (");
		ddl.append(" `ROW_ID` varchar(32) NOT NULL, ");
		List<LovGroupAttributes> list = lovGroupModel.getAttributes();
		for(LovGroupAttributes attr : list){
			String nullFlag = "NOT NULL";
			if("Y".equals(attr.getNullFlag())){
				nullFlag = "DEFAULT NULL";
			}
			ddl.append(String.format(" `%s` varchar(%d) %s , ",attr.getFieldCode(),attr.getLength(),nullFlag));
		}
		ddl.append(" PRIMARY KEY (`ROW_ID`) ");
		ddl.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ");
		baseDao.executeSQL(ddl.toString());
	}

}

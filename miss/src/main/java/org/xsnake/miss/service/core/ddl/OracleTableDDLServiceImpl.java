package org.xsnake.miss.service.core.ddl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xsnake.dao.BaseDao;
import org.xsnake.miss.api.core.entity.EntityAttributesDefinition;
import org.xsnake.miss.api.core.model.EntityDefinitionModel;
import org.xsnake.miss.exception.MissException;

/** 
* @author Jerry.Zhao 
* @version 创建时间：2017年5月17日 下午10:17:20 
* 
*/
@Service
public class OracleTableDDLServiceImpl implements ITableDDLService {

	@Autowired
	BaseDao baseDao;
	
	public void createTable(final String table,EntityDefinitionModel entityDefinitionModel) {
		StringBuffer ddl = new StringBuffer();
		ddl.append("CREATE TABLE ").append(table).append(" (");
		List<EntityAttributesDefinition> attributes = entityDefinitionModel.getAttributes();
		for(EntityAttributesDefinition attr : attributes){
			attr.validate();
			String nullFlag = "NOT NULL";
			if("Y".equals(attr.getNullFlag())){
				nullFlag = "";
			}
			ddl.append(String.format(" %s  %s  %s , ",attr.getFieldCode().toUpperCase(),getColumnType(attr),nullFlag));
		}
		ddl.append(" ROW_ID varchar2(32) not null ");
		ddl.append(" ) ");
		ddl.append(" pctfree 10 ");
		ddl.append(" initrans 1 ");
		ddl.append(" maxtrans 255 ");
		ddl.append(" storage ( ");
		ddl.append(" initial 64K ");
		ddl.append(" next 8K ");
		ddl.append(" minextents 1 ");
		ddl.append(" maxextents unlimited ");
		ddl.append(" ) ");
		baseDao.executeSQL(ddl.toString());
	}
	
	/**
	 * 更新字段后，同步更新具体的表结构
	 * 
	 * @param id
	 * @param attributes
	 * @param oldAttributes
	 */
	public void updateTable(String table, List<EntityAttributesDefinition> newAttributes, List<EntityAttributesDefinition> oldAttributes) {
		Map<String, EntityAttributesDefinition> newMap = toMap(newAttributes);
		Map<String, EntityAttributesDefinition> oldMap = toMap(oldAttributes);
		Set<String> allKeys = allKeys(newAttributes, oldAttributes);
		for (String key : allKeys) {
			EntityAttributesDefinition newEntityAttributesDefinition = newMap.get(key);
			EntityAttributesDefinition oldEntityAttributesDefinition = oldMap.get(key);
			String columnDDL = null;
			String remarkDDL = null;
			if (newEntityAttributesDefinition == null && oldEntityAttributesDefinition != null) {// 删除列
				columnDDL = "alter table "+table+" drop column " + key ;
			} else if (oldEntityAttributesDefinition == null && newEntityAttributesDefinition != null) {// 新增列
				columnDDL = "alter table "+table+" add "+key+" "+getColumnType(newEntityAttributesDefinition)+" " + ("N".equals(newEntityAttributesDefinition.getNullFlag()) ? "not null" : "");
				remarkDDL = "comment on column "+table+"."+key+" is '"+ newEntityAttributesDefinition.getRemark() +"'";
			} else {// 修改列
				columnDDL = "alter table "+table+" modify last_updated_date "+getColumnType(newEntityAttributesDefinition)+"  " + ("N".equals(newEntityAttributesDefinition.getNullFlag()) ? "not null" : "");
				remarkDDL = "comment on column "+table+"."+key+" is '"+ newEntityAttributesDefinition.getRemark() +"'";
			}
			if(columnDDL!=null){
				baseDao.executeSQL(columnDDL);
			}
			if(remarkDDL !=null){
				baseDao.executeSQL(remarkDDL);
			}
		}
	}
	
	private String getColumnType(EntityAttributesDefinition entityAttributesDefinition){
		String type = entityAttributesDefinition.getType();
		if("varchar2".equalsIgnoreCase(type)){
			type = type + "("+entityAttributesDefinition.getLength()+")";
		}else if("date".equalsIgnoreCase(type)){
			type = "date" ;
		}else if("number".equalsIgnoreCase(type)){
			type = type + "("+entityAttributesDefinition.getLength()+","+entityAttributesDefinition.getPrecision()+")";
		}else if ("fk".equalsIgnoreCase(type)){
			type = "varchar2(32)";
		}else{
			throw new MissException("1002");
		}
		return type;
	}
	
	private Map<String, EntityAttributesDefinition> toMap(List<EntityAttributesDefinition> attributes) {
		Map<String, EntityAttributesDefinition> map = new HashMap<String, EntityAttributesDefinition>();
		for (EntityAttributesDefinition d : attributes) {
			map.put(d.getFieldCode(), d);
		}
		return map;
	}

	private Set<String> allKeys(List<EntityAttributesDefinition> newAttributes,List<EntityAttributesDefinition> oldAttributes) {
		Set<String> keys = new HashSet<String>();
		for (EntityAttributesDefinition d : newAttributes) {
			keys.add(d.getFieldCode());
		}

		for (EntityAttributesDefinition d : oldAttributes) {
			keys.add(d.getFieldCode());
		}
		return keys;
	}

}

package org.xsnake.miss.service.lov;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.xsnake.dao.BaseDao;
import org.xsnake.dao.IPage;
import org.xsnake.dao.PageCondition;
import org.xsnake.miss.api.lov.ILovGroupService;
import org.xsnake.miss.api.lov.entity.LovGroup;
import org.xsnake.miss.api.lov.entity.LovGroupAttributes;
import org.xsnake.miss.api.lov.entity.LovMember;
import org.xsnake.miss.api.lov.model.LovGroupModel;
import org.xsnake.miss.exception.MissException;
import org.xsnake.miss.service.lov.ddl.TableFactory;
import org.xsnake.web.common.BeanUtils;

@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class LovGroupServiceImpl implements ILovGroupService {

	@Autowired
	BaseDao baseDao;
	
	public LovGroupModel get(String id) {
		if(StringUtils.isEmpty(id)){
			return null;
		}
		LovGroup lovGroup = baseDao.get(LovGroup.class, id);
		List<LovGroupAttributes> attributes = baseDao.findEntity(" from LovGroupAttributes where groupId = ? order by sortNo ");
		return new LovGroupModel(lovGroup,attributes);
	}

	public void update(LovGroupModel lovGroupModel) {
		if(lovGroupModel == null){
			throw new MissException("1000");
		}
		
		if(lovGroupModel.getLovGroup() == null || StringUtils.isEmpty(lovGroupModel.getLovGroup().getId())){
			throw new MissException("1001");
		}

		String groupId = lovGroupModel.getLovGroup().getId();
		LovGroup oldlovGroup = baseDao.get(LovGroup.class, groupId);
		BeanUtils.copyPropertiesIgnoreNull(lovGroupModel, oldlovGroup);
		baseDao.update(oldlovGroup);
		
		if(lovGroupModel.getAttributes() != null){
			baseDao.executeHQL(" delete from LovGroupAttributes where groupId = ? ",groupId); 
			List<LovGroupAttributes>  attributes = lovGroupModel.getAttributes();
			for(LovGroupAttributes attr : attributes){
				baseDao.save(attr);
			}
		}
		
	}

	public void save(LovGroupModel lovGroupModel) {
		if(lovGroupModel == null){
			throw new MissException("1002");
		}
		
		if(lovGroupModel.getLovGroup() == null ){
			throw new MissException("1003");
		}
		LovGroup lovGroup = lovGroupModel.getLovGroup();
		baseDao.save(lovGroup);
		
		if(lovGroupModel.getAttributes()!=null && lovGroupModel.getAttributes().size() > 0){
			TableFactory.createTable("MYSQL", baseDao, lovGroupModel);
			for(LovGroupAttributes attr : lovGroupModel.getAttributes()){
				attr.setGroupId(lovGroup.getId());
				baseDao.save(attr);
			}
		}
	}

	public void delete(String id) {
		if(StringUtils.isEmpty(id)){
			throw new MissException("1004");
		}
		LovGroup lovGroup = baseDao.get(LovGroup.class, id);
		//系统数据，不允许删除
		if("Y".equals(lovGroup.getSystemFlag())){
			throw new MissException("1005");
		}
		baseDao.executeHQL(" delete from LovGroupAttributes where groupId = ? ",id);
		baseDao.delete(lovGroup);
		baseDao.executeSQL("DROP TABLE "+TableFactory.TABLE_PREFIX+id+" ; ");
	}

	public IPage queryGroup(PageCondition condition) {
		StringBuffer hql = new StringBuffer(" from LovGroup g where 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		if(!StringUtils.isEmpty(condition.get("searchKey"))){
			hql.append(" and ( g.id like ? or g.name like ? or g.remark like ? ) ");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
		}
		if(!StringUtils.isEmpty(condition.get("systemFlag"))){
			hql.append(" and g.systemFlag = ? ");
			args.add(condition.get("systemFlag"));
		}
		if(!StringUtils.isEmpty(condition.get("treeFlag"))){
			hql.append(" and g.treeFlag = ? ");
			args.add(condition.get("treeFlag"));
		}
		
		if(!StringUtils.isEmpty(condition.get("status"))){
			hql.append(" and g.status = '1' ");
			args.add(condition.get("status"));
		}
		
		hql.append(" order by g.sortNo ");
		return baseDao.search(hql.toString(), args.toArray(),condition.getRows(),condition.getPage());
	}

	public IPage queryMember(String id, PageCondition condition) {
		StringBuffer hql = new StringBuffer(" from LovMember m where m.groupId = ? ");
		List<Object> args = new ArrayList<Object>();
		args.add(id);
		
		if(!StringUtils.isEmpty(condition.get("searchKey"))){
			hql.append(" and ( m.code like ? or m.name like ? ) ");
			args.add("%"+condition.get("searchKey")+"%");
			args.add("%"+condition.get("searchKey")+"%");
		}
		
		if(!StringUtils.isEmpty(condition.get("status"))){
			hql.append(" and m.status = '1' ");
			args.add(condition.get("status"));
		}
		
		hql.append(" order by m.sortNo ");
		return baseDao.search(hql.toString(), args.toArray(),condition.getRows(),condition.getPage());
	}

	public List<LovMember> detail(String id) {
		if(StringUtils.isEmpty(id)){
			return new ArrayList<LovMember>();
		}
		StringBuffer hql = new StringBuffer(" from LovMember m where m.groupId = ? order by m.sortNo ");
		return baseDao.findEntity(hql.toString(),id);
	}

}

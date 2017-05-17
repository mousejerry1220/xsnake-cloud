//package org.xsnake.miss.service.lov;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.xsnake.dao.BaseDao;
//import org.xsnake.miss.api.lov.ILovMemberService;
//import org.xsnake.miss.api.lov.entity.LovGroup;
//import org.xsnake.miss.api.lov.entity.LovMember;
//import org.xsnake.miss.api.lov.model.LovMemberModel;
//import org.xsnake.miss.exception.MissException;
//import org.xsnake.miss.service.lov.ddl.TableFactory;
//import org.xsnake.web.common.BeanUtils;
//
///** 
//* @author Jerry.Zhao 
//* @version 创建时间：2017年5月12日 下午11:12:46 
//* 
//*/
//@Service
//public class LovMemberServiceImpl implements ILovMemberService{
//
//	@Autowired
//	BaseDao baseDao;
//	
//	public LovMemberModel get(String id) {
//		if(StringUtils.isEmpty(id)){
//			return null;
//		}
//		LovMember lovMember = baseDao.get(LovMember.class, id);
//		Map<String,Object> attributesMap = baseDao.findUniqueBySql4Map("select * from " + TableFactory.TABLE_PREFIX+lovMember.getGroupId() + " where row_id = ? ", id);
//		Map<String,String> map = new HashMap<String, String>();
//		for(Entry<String, Object> entry : attributesMap.entrySet()){
//			map.put(entry.getKey(), String.valueOf(entry.getValue()));
//		}
//		return new LovMemberModel(lovMember, map);
//	}
//
//	public void update(LovMemberModel lovMemberModel) {
//		if(lovMemberModel == null){
//			throw new MissException("1100");
//		}
//		if(lovMemberModel.getLovMember() == null || StringUtils.isEmpty(lovMemberModel.getLovMember().getId())){
//			throw new MissException("1101");
//		}
//		
//		String lovMemberId = lovMemberModel.getLovMember().getId();
//		LovMember oldLovMember = baseDao.get(LovMember.class, lovMemberId);
//		LovMember lovMember = lovMemberModel.getLovMember();
//		
//		//不更新字段设置为null
//		lovMember.setCode(null);
//		lovMember.setGroupId(null);
//		BeanUtils.copyPropertiesIgnoreNull(lovMember, oldLovMember);
//		baseDao.update(oldLovMember);
//		
//		if(lovMemberModel.getAttributesMap() != null){
//			StringBuffer sql = new StringBuffer(" update ").append( oldLovMember.getGroupId()).append(" set ");
//			List<Object> args = new ArrayList<Object>();
//			for(Entry<String, String> entry : lovMemberModel.getAttributesMap().entrySet()){
//				sql.append(entry.getKey()).append(" = ? ").append(" , ");
//				args.add(entry.getValue());
//			}
//			sql.append(" row_id = ? where row_id = ? ");
//			args.add(lovMemberId);
//			args.add(lovMemberId);
//			baseDao.executeSQL(sql.toString(),args.toArray());
//		}
//	}
//
//	public void save(LovMemberModel lovMemberModel) {
//		if(lovMemberModel == null){
//			throw new MissException("1102");
//		}
//		if(lovMemberModel.getLovMember() == null){
//			throw new MissException("1103");
//		}
//		LovMember lovMember = lovMemberModel.getLovMember();
//		if(StringUtils.isEmpty(lovMember.getGroupId())){
//			throw new MissException("1104");
//		}
//		LovGroup lovGroup = baseDao.get(LovGroup.class, lovMember.getGroupId());
//		if(lovGroup == null){
//			throw new MissException("1105");
//		}
//		lovMember.setStatus("1");
//		baseDao.save(lovMember);
//		if(lovMemberModel.getAttributesMap() != null && lovMemberModel.getAttributesMap().size() > 0){
//			StringBuffer sql = new StringBuffer(" insert into ").append( lovMember.getGroupId()).append(" ( ");
//			List<Object> args = new ArrayList<Object>();
//			for(Entry<String, String> entry : lovMemberModel.getAttributesMap().entrySet()){
//				sql.append(entry.getKey()).append(" , ");
//				args.add(entry.getValue());
//			}
//			sql.append(" row_id ) values ( ");
//			for(int i=0;i<lovMemberModel.getAttributesMap().size();i++){
//				sql.append(" ?, ");
//			}
//			sql.append(" ? )");
//			args.add(lovMember.getId());
//			baseDao.executeSQL(sql.toString(),args.toArray());
//		}
//	}
//
//	public void delete(String id) {
//		LovMember lovMember = baseDao.get(LovMember.class, id);
//		lovMember.setStatus("0");
//		baseDao.update(lovMember);
//	}
//
//}
package org.xsnake.cloud.service.lov.api;
/**
 * 通用的维度信息定义。将一类具有相同特征的数据归纳为一个集合，这里只是对集合的定义，定义下的具体数据参考LOV_MEMBER
 * 维度主要用途：
 * 1、下拉选择类型的定义（如：某一实体的状态、类型）。
 * 2、树形结构的数据或者级联关联的数据定义（如：地理位置）。
 * 3、实体类型定义。
 */

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xsnake.cloud.common.feign.FeignConfiguration;
import org.xsnake.cloud.common.search.IPage;
import org.xsnake.cloud.service.lov.api.entity.LovGroup;
import org.xsnake.cloud.service.lov.api.entity.LovGroupCondition;
import org.xsnake.cloud.service.lov.api.entity.LovGroupForm;

@FeignClient(name = "lov", configuration = FeignConfiguration.class)
public interface IGroupService {
 
	@RequestMapping(value="/group/save",method=RequestMethod.POST)
	void save(@RequestBody LovGroupForm lovGroupForm);
	
	@RequestMapping(value="/group/update",method=RequestMethod.POST)
	void update(@RequestBody LovGroupForm lovGroupForm);
	
	@RequestMapping(value="/group/delete",method=RequestMethod.POST)
	void delete(@RequestBody LovGroupForm form);
	
	@RequestMapping(value="/group/search",method={RequestMethod.POST,RequestMethod.GET})
	IPage search(LovGroupCondition condition);
	
	@RequestMapping(value="/group/get",method={RequestMethod.POST,RequestMethod.GET})
	LovGroup get(@RequestParam(name="code") String code);

}

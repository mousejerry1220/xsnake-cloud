package org.xsnake.cloud.service.lov.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xsnake.cloud.common.feign.FeignConfiguration;
import org.xsnake.cloud.common.search.IPage;
import org.xsnake.cloud.service.lov.api.entity.LovMember;
import org.xsnake.cloud.service.lov.api.entity.LovMemberCondition;
import org.xsnake.cloud.service.lov.api.entity.LovMemberForm;

@FeignClient(name = "lov", configuration = FeignConfiguration.class)
public interface IMemberService {
	
	@RequestMapping(value="/member/queryForActive",method={RequestMethod.POST,RequestMethod.GET})
	List<LovMember> queryForActive(@RequestParam("groupCode") String groupCode);
	
	@RequestMapping(value="/member/queryForAll",method={RequestMethod.POST,RequestMethod.GET})
	List<LovMember> queryForAll(@RequestParam("groupCode") String groupCode);
	
	@RequestMapping(value="/member/search",method={RequestMethod.POST,RequestMethod.GET})
	IPage search(LovMemberCondition condition);
	
	@RequestMapping(value="/member/searchByTree",method={RequestMethod.POST,RequestMethod.GET})
	IPage searchByTree(LovMemberCondition condition);
	
	@RequestMapping(value="/member/get",method={RequestMethod.POST,RequestMethod.GET})
	LovMember get(@RequestParam(name="id") String id);
	
	@RequestMapping(value="/member/save",method=RequestMethod.POST)
	void save(@RequestBody LovMemberForm form);
	
	@RequestMapping(value="/member/delete",method=RequestMethod.POST)
	void delete(@RequestBody LovMemberForm form);
	
	@RequestMapping(value="/member/update",method=RequestMethod.POST)
	void update(@RequestBody LovMemberForm form);
	
}

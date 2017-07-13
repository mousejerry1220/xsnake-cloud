package org.xsnake.cloud.web.bee.select;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xsnake.cloud.common.action.BaseAction;

@Controller
public class SelectAction extends BaseAction{

	/**
	 * 接收请求打开请求页面
	 * @param group 选择的主体对象
	 * @param range 选择的范围：有效、无效、全部
	 * @param permission 选择的权限：有权限，无权限，全部
	 * @param multiple 可多选 
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
    public String select(String group,String range,String permission,Boolean multiple,String callback,Integer page,Integer size,Model model){
		
		if(multiple == null){
			multiple = false;
		}
		
		//这里生成要选择对象的列描述
		model.addAttribute("colModel","colModel");
		//查找出来显示的标题
		model.addAttribute("title","title");
		
		model.addAttribute("multiple",multiple);
		
		//选择结束后将选择的值回调告知调用方
		model.addAttribute("callback",callback);
		
//		[
//			{name:'id',lable:'id', width:60, sorttype:"int", editable: false},
//			{name:'sdate',lable:'sdate',width:90, editable:true, edittype:"date",sorttype:"date"}, 
//			{name:'name',lable:'name', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
//			{name:'stock',lable:'stock', width:70, editable: true,edittype:"checkbox" },
//			{name:'ship',lable:'ship', width:90, editable: true,edittype:"select",editoptions:{value:"FedEx:FE;InTime:IN;TNT:TN;ARAMEX:AR"}},
//			{name:'note',lable:'note', width:150, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}} 
//		]
		
		
        return forward("select");
    }
	
	/**
	 * 通过参数查询出结果
	 * @param group
	 * @param range
	 * @param permission
	 * @param multiple
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String page(String group,String range,String permission,boolean multiple,Integer page,Integer size){
		return sendSuccessMessage();
	}
}

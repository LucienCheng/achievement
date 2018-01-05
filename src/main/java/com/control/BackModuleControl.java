package com.control;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Module;
import com.service.ModuleService;
@Controller
public class BackModuleControl {
	
	@Resource
	private ModuleService moduleService;
	//保存模块
	@RequestMapping(value="/back/user/saveModule",method=RequestMethod.POST)
	@ResponseBody
	public String saveModule(@RequestBody Module module) {
		int result=moduleService.insertModule(module);
		if(result==1){
			return "success";
		}
		else {
			return "failure";
		}
	}
	//更新模块
	@RequestMapping(value="/back/user/updateModule",method=RequestMethod.POST)
	@ResponseBody
	public String updateModule(@RequestBody Module module) {
		int result=moduleService.updateModule(module);
		if(result==1){
			return "success";
		}
		else {
			return "failure";
		}
	}
	//删除模块
	@RequestMapping(value="/back/user/deleteModule",method=RequestMethod.POST)
	@ResponseBody
	public String deleteModule(@RequestBody List<Integer> moduleIds) {
		int result=moduleService.deleteModule(moduleIds);
		if(result>0){
			return "success";
		}
		else {
			return "failure";
		}
	}
	//获得单个模块
	@RequestMapping(value="/back/user/{moduleId}",method=RequestMethod.GET)
	@ResponseBody
	public Module getModule(@PathVariable("moduleId") int moduleId){
		 Module module=moduleService.selectModuleByModId(moduleId);
		 return module;
	}
	
	
	

}

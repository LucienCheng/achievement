package com.control;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Module;
import com.service.ModuleService;
@Controller
public class BackModuleControl {
	
	@Resource
	private ModuleService moduleService;
	//返回view的前缀
	private final static String prefixName="jsp/back/uploader/";
	private static String getViewName(String viewName) {
		return prefixName+viewName;
	}
	@RequestMapping(name="/uploader/module/saveMod",method=RequestMethod.POST)
	public String saveMod(Module module,Model model,HttpServletRequest request) throws UnsupportedEncodingException{
		module.setAchId(105);
		moduleService.insertModule(module);
		model.addAttribute("module", module);
		return getViewName("showUeditor");
	}
	
	
	

}

package com.control;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.AchievementService;

@Controller
public class FrontControl {
	@Resource
	private AchievementService achievementService;
	@RequestMapping(value="/front",method={RequestMethod.GET})
	public String front() {
		
		return "/front/index";
	}
	
	@RequestMapping(value="/front/video/{achievementId}",method={RequestMethod.GET})
	public String getAchievementVideo(@PathVariable("achievementId") int achievementId) {
		
		return "/front/video";
	}
	
	@RequestMapping(value="/front/modules/{achievementId}/{moduleId}",method={RequestMethod.GET})
	public String getAchievementModules(@PathVariable("achievementId") int achievementId,
			@PathParam("moduleId") int moduleId) {
		return "/front/modules";
	}
	
	@RequestMapping(value="/front/HotAchievement/{start}",method={RequestMethod.GET})
	public String getHotAchievement(@PathVariable("start") int start) {
		return "/front/modules";
	}
	@RequestMapping(value="/front/NewAchievement/{start}",method={RequestMethod.GET})
	public String getNewAchievement(@PathVariable("start") int start) {
		
		return "/front/modules";
	}
	
	
	
}

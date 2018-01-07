package com.control;

import java.util.List;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.AchievementCondition;
import com.entity.Module;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.SlideShowService;

@Controller
public class FrontControl {
	@Resource
	private AchievementService achievementService;
	@Resource
	private SlideShowService slideShowService;
	@Resource
	private ModuleService moduleService;
	private final int count=10;
	//一级界面
	@RequestMapping(value="/front/index",method={RequestMethod.GET})
	public String front(Model model) {
		model.addAttribute("hot", achievementService.getHotAchi(null, null, null, 0, 5));
		model.addAttribute("new",achievementService.getNewAchi(null, null, null, 0, 5));
		model.addAttribute("slideShow", slideShowService.selectSlideShow());
		return "/front/index";
	}
	
	@RequestMapping(value="/front/{achievementId}/video",method={RequestMethod.GET})
	public String getAchievementVideo(@PathVariable("achievementId") int achievementId,Model model) {
		model.addAttribute("video", achievementService.getAchiByAchId(achievementId).getAchVideoPath());
		return "/front/video";
	}
	@RequestMapping(value="/front/modules/{achId}",method={RequestMethod.GET})
	public String getAchievementModules(@PathParam("achId") int achId,Model model) {
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		return "/front/modules";
	}
	@RequestMapping(value="/front/modules/{achId}/{moduleId}",method={RequestMethod.GET})
	public String getAchievementModule(@PathParam("moduleId") int moduleId,@PathParam("achId") int achId,
			Model model) {
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		model.addAttribute("moduleId", moduleId);
		return "/front/modules";
	}
	
	@RequestMapping(value="/front/HotAchievement/{start}",method={RequestMethod.GET})
	public String getHotAchievement(@PathVariable("start") int start,AchievementCondition condition) {
		achievementService.getHotAchi(condition.getAuthorName(), condition.getAchStartTime(), 
				condition.getAchEndTime(), start, count);
		return "/front/second";
	}
	@RequestMapping(value="/front/NewAchievement/{start}",method={RequestMethod.GET})
	public String getNewAchievement(@PathVariable("start") int start,AchievementCondition condition) {
		
		return "/front/second";
	}
	
	
	
}

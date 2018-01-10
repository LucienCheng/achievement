package com.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Achievement;
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
	//三级页面的视频，同时增加点击量
	@RequestMapping(value="/front/{achievementId}/video",method={RequestMethod.GET})
	@Transactional
	public String getAchievementVideo(@PathVariable("achievementId") int achievementId,Model model) {

		Achievement achievement=achievementService.getAchiByAchId(achievementId);
		model.addAttribute("video", achievement.getAchVideoPath());
		achievement.setAchCTR(achievement.getAchCTR()+1);
		achievementService.updateAchievement(achievement);
		return "/front/video";
	}
	//三级页面模块，初始页面
	@RequestMapping(value="/front/modules/{achId}",method={RequestMethod.GET})
	public String getAchievementModules(@PathParam("achId") int achId,Model model) {
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		return "/front/modules";
	}
	//三级页面模块，具体页面
	@RequestMapping(value="/front/modules/{achId}/{moduleId}",method={RequestMethod.GET})
	public String getAchievementModule(@PathParam("moduleId") int moduleId,@PathParam("achId") int achId,
			Model model) {
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		model.addAttribute("moduleId", moduleId);
		return "/front/modules";
	}
	//二级页面
	@RequestMapping(value="/front/HotAchievement",method={RequestMethod.GET})
	public String getHotAchievement(Model model) {
		model.addAttribute("achievements", achievementService.getHotAchi(null, null, 
				null, 0, count));
		return "/front/second";
	}
	
	@RequestMapping(value="/front/HotAchievement/{start}",method={RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getHotAchievement(@PathVariable("start") Integer start,AchievementCondition condition) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("statue", "failure");
		map.put("achievements", achievementService.getHotAchi(condition.getAuthorName(),
				condition.getAchStartTime(), condition.getAchEndTime(), start, count));
		map.put("statue", "success");
		return map;
	}
	//二级页面
	@RequestMapping(value="/front/NewAchievement",method={RequestMethod.GET})
	public String getNewAchievement(Model model) {
		model.addAttribute("achievements", 
				achievementService.getNewAchi(null, null, 
				null, 0, count));
		return "/front/second";
	}
	@RequestMapping(value="/front/NewAchievement/{start}",method={RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> getNewAchievement(@PathVariable("start") Integer start,AchievementCondition condition) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("statue", "failure");
		map.put("achievements", achievementService.getHotAchi(condition.getAuthorName(),
				condition.getAchStartTime(), condition.getAchEndTime(), start, count));
		map.put("statue", "success");
		return map;
	}
}

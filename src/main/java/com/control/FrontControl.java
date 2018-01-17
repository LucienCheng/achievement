package com.control;

import java.util.HashMap;
import java.util.List;
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
	private final int count = 10;

	// 一级界面
	@RequestMapping(value = "/front/index", method = { RequestMethod.GET })
	public String front(Model model) {
		model.addAttribute("hotAchievements",
				achievementService.getHotAchi(null, null, null, 0, 5));
		model.addAttribute("newAchievements",
				achievementService.getNewAchi(null, null, null, 0, 5));
		model.addAttribute("slideShow", slideShowService.selectSlideShow());
		return "/front/index";
	}

	// 二级页面最热
	

	@RequestMapping(value = "/front/HotAchievement/{start}", method = { RequestMethod.GET })
	public String getHotAchievement(Model model,
			@PathVariable("start") Integer start, AchievementCondition condition) {
		
		model.addAttribute("achievements", achievementService.getHotAchi(
				condition.getAuthorName(), condition.getAchStartTime(),
				condition.getAchEndTime(), start, count));
		return "/front/second";
	}

	// 二级页面最新

	@RequestMapping(value = "/front/NewAchievement/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public String getNewAchievement(Model model,
			@PathVariable("start") Integer start, AchievementCondition condition) {
		model.addAttribute("achievements", achievementService.getHotAchi(
				condition.getAuthorName(), condition.getAchStartTime(),
				condition.getAchEndTime(), start, count));
		
		return "/front/second";
	}

	// 三级页面的视频，同时增加点击量
	@RequestMapping(value = "/front/{achievementId}/video", method = { RequestMethod.GET })
	@Transactional
	public String getAchievementVideo(
			@PathVariable("achievementId") int achievementId, Model model) {

		Achievement achievement = achievementService
				.getAchiByAchId(achievementId);
		if(achievement.getAchStatus()!=1){
			model.addAttribute("error", "成果更新状态了，不能查看，需要回退到上一步");
			return "forward:/front/second";
		}
		else {
			model.addAttribute("video", achievement.getAchVideoPath());
			achievement.setAchCTR(achievement.getAchCTR() + 1);
			achievementService.updateAchievement(achievement);
			return "/front/video";
		}
		
	}
	// 三级页面模块，初始页面
	@RequestMapping(value = "/front/modules/{achId}", method = { RequestMethod.GET })
	public String getAchievementModules(@PathVariable("achId") int achId,
			Model model) {
		Achievement achievement = achievementService
				.getAchiByAchId(achId);
		if (achievement.getAchStatus()!=1) {
			model.addAttribute("error", "成果更新状态了，不能查看，需要回退到上一步");
			return "forward:/front/second";
		}
		else {
			model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
			return "/front/modules";
		}
		
	}

}

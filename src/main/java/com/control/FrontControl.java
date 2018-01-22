package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.SlideShowService;

@Controller
public class FrontControl {
	@Resource
	private AchievementService achievementService;
	@Resource
	private SlideShowService slideShowService;
	@Resource(name="moduleImpl")
	private ModuleService moduleService;
	private final int count = 10;
//检验某个成果是否为可以发布的状态。通过status判断
	
	// 一级界面
	@RequestMapping(value = "/front/index", method = { RequestMethod.GET })
	public String front(Model model) throws JsonProcessingException {
		model.addAttribute("hotAchievements",
				achievementService.getHotAchi(null, null, null, 0, 4));
		model.addAttribute("newAchievements",
				achievementService.getNewAchi(null, null, null, 0, 6));
		Map<String, Integer> map=new HashMap<String, Integer>();
		List<Achievement> achievements=slideShowService.selectSlideShow();
		List<String> image=new ArrayList<String>();
		for (Achievement achievement : achievements) {
			image.add(achievement.getAchImagePath());
			map.put(achievement.getAchImagePath(), achievement.getAchId());
		}
		JSONArray slideShow=new JSONArray(image);
		 ObjectMapper mapper =new ObjectMapper();
		model.addAttribute("slideShowMap", mapper.writeValueAsString(map));
		model.addAttribute("slideShow", slideShow);
		return "/front/index";
	}

	// 二级页面最热
	

	@RequestMapping(value = "/front/HotAchievement/{start}", method = { RequestMethod.GET,RequestMethod.POST })
	public String getHotAchievement(Model model, 
			@PathVariable("start") Integer start, AchievementCondition condition) {
		if (start==null ||start==0) {
			start=1;
		}
		if (condition != null
				&& condition.getAchStartTime() != null
				&& condition.getAchEndTime() != null) {
			if (condition.getAchStartTime().length() == 0) {
				condition.setAchStartTime(null);
			}
			if (condition.getAchEndTime().length() == 0) {
				condition.setAchEndTime(null);
			}
		}
		condition.setAchStatus(1);
		int totalCount = achievementService.getCount(condition);
		int totalPage = (totalCount + count - 1) / count;
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("curPage", start);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("achievements", achievementService.getHotAchi(
				condition.getAuthorName(), condition.getAchStartTime(),
				condition.getAchEndTime(), (start-1)*count, count));
		model.addAttribute("condition",condition );
		return "/front/second";
	}

	// 二级页面最新

	@RequestMapping(value = "/front/NewAchievement/{start}", method = { RequestMethod.GET ,RequestMethod.POST })
	public String getNewAchievement(Model model,
			@PathVariable("start") Integer start, AchievementCondition condition) {
		if (start==null ||start==0) {
			start=1;
		}
		if (condition != null
				&& condition.getAchStartTime() != null
				&& condition.getAchEndTime() != null) {
			if (condition.getAchStartTime().length() == 0) {
				condition.setAchStartTime(null);
			}
			if (condition.getAchEndTime().length() == 0) {
				condition.setAchEndTime(null);
			}
		}
		condition.setAchStatus(1);
		int totalCount = achievementService.getCount(condition);
		int totalPage = (totalCount + count - 1) / count;
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("curPage", start);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("achievements", achievementService.getNewAchi(
				condition.getAuthorName(), condition.getAchStartTime(),
				condition.getAchEndTime(), (start-1)*count, count));

		model.addAttribute("condition",condition );
		return "/front/second";
	}

	// 三级页面的视频，同时增加点击量
	@RequestMapping(value = "/front/{achievementId}/video", method = { RequestMethod.GET,RequestMethod.POST })
	@Transactional
	public String getAchievementVideo(String Url,AchievementCondition condition,RedirectAttributes redirectAttributes,
			@PathVariable("achievementId") int achievementId, Model model) {
System.out.println(Url);
		Achievement achievement = achievementService
				.getAchiByAchId(achievementId);
		
		if(achievement.getAchStatus()!=1){
			System.out.println(achievement.getAchId());
			redirectAttributes.addFlashAttribute("error", "error");
			return "redirect:"+Url;
		}
		else {
			model.addAttribute("video", achievement.getAchVideoPath());
			model.addAttribute("achId", achievementId);
			model.addAttribute("Url", Url);
			achievement.setAchCTR(achievement.getAchCTR() + 1);
			achievementService.updateAchievement(achievement);
			return "/front/video";
		}
		
	}
	// 三级页面模块，初始页面
	@RequestMapping(value = "/front/modules/{achId}", method = { RequestMethod.GET,RequestMethod.POST })
	public String getAchievementModules(@PathVariable("achId") int achId,String Url,RedirectAttributes redirectAttributes,
			AchievementCondition condition,
			Model model) {
		Achievement achievement = achievementService
				.getAchiByAchId(achId);
		System.out.println(Url);
		if (achievement.getAchStatus()!=1) {
			System.out.println(achievement.getAchId());
			redirectAttributes.addFlashAttribute("error", "error");
			return "redirect:"+Url;
		}
		else {
			model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
			model.addAttribute("achievement", achievement);
			return "/front/modules";
		}
		
	}

}

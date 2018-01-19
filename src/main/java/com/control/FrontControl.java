package com.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.json.JsonArray;
import javax.websocket.server.PathParam;

import org.json.JSONArray;
import org.json.JSONObject;
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
				achievementService.getHotAchi(null, null, null, 0, 5));
		model.addAttribute("newAchievements",
				achievementService.getNewAchi(null, null, null, 0, 5));
		Map<String, Integer> map=new HashMap<String, Integer>();
		List<Achievement> achievements=slideShowService.selectSlideShow();
		for (Achievement achievement : achievements) {
			map.put(achievement.getAchImagePath(), achievement.getAchId());
		}
		 ObjectMapper mapper =new ObjectMapper();
		model.addAttribute("slideShow", mapper.writeValueAsString(map));
		return "/front/index";
	}

	// 二级页面最热
	

	@RequestMapping(value = "/front/HotAchievement/{start}", method = { RequestMethod.GET,RequestMethod.POST })
	public String getHotAchievement(Model model, 
			@PathVariable("start") Integer start, AchievementCondition condition) {
		if (start==null ||start==0) {
			start=1;
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
		return "/front/second";
	}

	// 二级页面最新

	@RequestMapping(value = "/front/NewAchievement/{start}", method = { RequestMethod.GET ,RequestMethod.POST })
	public String getNewAchievement(Model model,
			@PathVariable("start") Integer start, AchievementCondition condition) {
		if (start==null ||start==0) {
			start=1;
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
		return "/front/second";
	}

	// 三级页面的视频，同时增加点击量
	@RequestMapping(value = "/front/{achievementId}/video", method = { RequestMethod.GET,RequestMethod.POST })
	@Transactional
	public String getAchievementVideo(String Url,
			@PathVariable("achievementId") int achievementId, Model model) {

		Achievement achievement = achievementService
				.getAchiByAchId(achievementId);
		
		if(achievement.getAchStatus()!=1){
			System.out.println(achievement.getAchId());
			model.addAttribute("error", "error");
			return "forward:"+Url;
		}
		else {
			model.addAttribute("video", achievement.getAchVideoPath());
			model.addAttribute("achId", achievementId);
			achievement.setAchCTR(achievement.getAchCTR() + 1);
			achievementService.updateAchievement(achievement);
			return "/front/video";
		}
		
	}
	// 三级页面模块，初始页面
	@RequestMapping(value = "/front/modules/{achId}", method = { RequestMethod.GET,RequestMethod.POST })
	public String getAchievementModules(@PathVariable("achId") int achId,String Url,Model model) {
		Achievement achievement = achievementService
				.getAchiByAchId(achId);
		if (achievement.getAchStatus()!=1) {
			System.out.println(achievement.getAchId());
			model.addAttribute("error", "error");
			return "forward:"+Url;
		}
		else {
			model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
			return "/front/modules";
		}
		
	}

}

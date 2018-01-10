package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Module;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;
import com.service.UserService;

@Controller
public class BackUserControll {
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource(name = "moduleImpl")
	private ModuleService moduleService;
	@Resource(name = "UserServiceImpl")
	private UserService userService;
	private final int count = 10;// 显示的条数

	// 首页显示待审核
	@RequestMapping(value = "/back/user/achievement/", method = { RequestMethod.GET })
	public String index(HttpSession session, Model model,
			AchievementCondition condition) {
		model.addAttribute("achievements", achievementService
				.getAchiLockCondition((Integer) session.getAttribute("userId"),
						null, 0, condition.getAchStartTime(),
						condition.getAchEndTime(), null, null,
						condition.getAuditorName(), condition.getAchName(),
						null, 0, 0, count));
		model.addAttribute("totalCount", achievementService.getCount(condition));
		return "/back/user/userIndex";
	}
//获取成果
	@RequestMapping(value = "/back/user/achievement/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> userSearchAchievement(HttpSession session,
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		map.put("achievements", achievementService.getAchiLockCondition(
				(Integer) session.getAttribute("userId"), null,
				condition.getAchStatus(), condition.getAchStartTime(),
				condition.getAchEndTime(), null, null,
				condition.getAuditorName(), condition.getAchName(), null, 0,
				start, count));
		map.put("totalCount", achievementService.getCount(condition));
		map.put("statue", "success");
		return map;
	}

	// 个人资料
	@RequestMapping(value = "/back/admin/personInfo", method = { RequestMethod.GET })
	public String personInfo(HttpSession session, Model model) {
		model.addAttribute("user",
				userService.getUserById((int) session.getAttribute("userId")));
		return "/back/personInfo";
	}

	// 在修改某个模块的时候，就会发送一个模块过去。
	@RequestMapping(value = "/back/user/{moduleId}", method = RequestMethod.GET)
	public String getModule(@PathVariable("moduleId") int moduleId, Model model) {
		Module module = moduleService.selectModuleByModId(moduleId);
		model.addAttribute("module", module);
		return "/back/user/module";
	}

	// 进入修改成果界面
	@RequestMapping(value = "/back/user/achievement/modify", method = { RequestMethod.POST })
	@Transactional
	public String modifyAchievement(int achId, Model model, int achStatus) {
		// 获得成果的基本信息
		Achievement achievement = achievementService.getAchiByAchId(achId);
		if (achStatus == 1) {
			List<Integer> oldModuleIds = new ArrayList<Integer>();
			// 通过搜索成果的id获得一份module。
			List<Module> modules = moduleService.selectModuleByAchId(achId);
			for (Module module : modules) {
				oldModuleIds.add(module.getModId());
			}
			// 重新拷贝一份，重新把插入的主键重置给modId属性
			moduleService.insertModules(modules);
			List<Integer> newModuleIds = new ArrayList<Integer>();
			// 将复制一份的module进行复制。
			for (Module module : modules) {
				newModuleIds.add(module.getModId());
			}
			model.addAttribute("newModuleIds", newModuleIds);
			model.addAttribute("oldModuleIds", oldModuleIds);
		}
		model.addAttribute("achievement", achievement);
		return "/back/user/achievement";
	}
	//添加achievement
		@RequestMapping(value = "/back/user/achievement/add", method = { RequestMethod.GET })
		public String addAchievement(){
			return "/back/user/achievement";
		}
	// 保存成果。为了防止多次保存造成同步问题，需要进行saveCount的次数保存
	@RequestMapping(value = "/back/user/achievement/save", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> updateModAchievement(
			@RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement, List<Integer> moduleIds,int saveCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		String imagePath=null;
		String videoPath=null;
		// 默认是失败的
		map.put("statue", "failure");
		//第一次保存
		if(saveCount==1){
			achievement.setAchDate(TimeToolService.getCurrentTime());
			// 如果保存的是一个新的成果
			if (achievement.getAchId() == null) {
				imagePath = achievementService.saveImagine(image, request);
				 videoPath = achievementService.saveVideo(video, request);
				achievement.setAchImagePath(imagePath);
				achievement.setAchVideoPath(videoPath);
				int achiResult = achievementService.insertAchi(achievement);
				int modResult = moduleService.updateModuleByachId(moduleIds,
						achievement.getAchId());
				if (achiResult > 0 && modResult > 0) {
					map.put("image", imagePath);
					map.put("video", videoPath);
					
				}
			} else {
				Achievement oldAchievement = achievementService.getAchiByAchId(achievement.getAchId());
				
				if (image != null) {
					 imagePath = achievementService.saveImagine(image,
							request);
					achievement.setAchImagePath(imagePath);
					map.put("image", imagePath);
				}
				if (video != null) {
					 videoPath = achievementService.saveVideo(video, request);
					achievement.setAchVideoPath(videoPath);
					map.put("video", videoPath);
				}
				
				// 更新待审核,删除之前新建的module
				if (oldAchievement.getAchStatus() == 0) {
					achievementService.updateAchievement(achievement);
					moduleService.deleteModule(moduleIds);
				}
				// 更新审核通过
				else if (oldAchievement.getAchStatus() == 1) {
					// 使用的是修改的更新
					achievementService.updateAchiModify(achievement);
					// 更新模块内容
					moduleService.updateModuleByachId(moduleIds,achievement.getAchId());
				}
				// 更新未通过,删除之前新建的module
				else {
					achievement.setAchStatus(0);
					achievementService.updateAchievement(achievement);
					moduleService.deleteModule(moduleIds);
				}
			}
			List<Module> modules=moduleService.selectModuleByAchId(achievement.getAchId());
			moduleIds.clear();
			for (Module module : modules) {
				moduleIds.add(module.getModId());
			}
			map.put("moduleIds",moduleIds );
			map.put("statue", "success");
		}
		return map;
	}

	
	// 删除achievement
	@RequestMapping(value = "/back/user/achievement/delete/{start}", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deleteAchievement(List<Integer> achievements,
			AchievementCondition condition, @PathVariable("start") int start,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		int result = achievementService.deleteAchis(achievements);
		if (result > 0) {
			List<Achievement> achievements2 = achievementService.getAchiLockCondition(
							(Integer) session.getAttribute("userId"), null,
							condition.getAchStatus(), condition.getAchStartTime(),
							condition.getAchEndTime(), null, null,
							condition.getAuditorName(), condition.getAchName(), null, 0,
							start, count);
			map.put("achievements", achievements2);
			map.put("statue", "success");
		}
		return map;
	}

	// 保存模块
	@RequestMapping(value = "/back/user/saveModule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveModule(Module module) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		int result = moduleService.insertModule(module);
		if (result == 1) {
			map.put("statue", "success");
		}
		return map;
	}

	// 更新模块
	@RequestMapping(value = "/back/user/updateModule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateModule(@RequestBody Module module) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		int result = moduleService.updateModule(module);
		if (result == 1) {
			map.put("statue", "success");
		}
		return map;

	}

	// 删除模块
	@RequestMapping(value = "/back/user/deleteModule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModule(@RequestBody List<Integer> moduleIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		int result = moduleService.deleteModule(moduleIds);
		if (result > 0) {
			map.put("statue", "success");
		}
		return map;
	}

}

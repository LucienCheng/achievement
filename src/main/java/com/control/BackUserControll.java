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

import com.dao.AuditMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Module;
import com.entity.User;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;
import com.service.UserService;

//上传人员控制区域
@Controller
public class BackUserControll {
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource(name = "moduleImpl")
	private ModuleService moduleService;
	@Resource(name = "UserServiceImpl")
	private UserService userService;
	@Resource
	private AuditMapper auditMapper;
	private final int count = 10;// 显示的条数

	// 首页显示审核已通过
	@RequestMapping(value = "/back/user/achievement/", method = { RequestMethod.GET })
	public String index(HttpSession session, Model model,
			AchievementCondition condition) {
		model.addAttribute("achievements", achievementService
				.getAchiLockCondition((Integer) session.getAttribute("userId"),
						null, 1, condition.getAchStartTime(),
						condition.getAchEndTime(), null, null,
						condition.getAuditorName(), condition.getAchName(),
						null, 0, 0, count));
		model.addAttribute("totalCount", achievementService.getCount(condition));
		return "/back/user/userIndex";
	}

	// 获取成果userindex使用的ajax
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
	@RequestMapping(value = "/back/user/personInfo", method = { RequestMethod.GET })
	public String personInfo(HttpSession session, Model model) {
		model.addAttribute("user",
				userService.getUserById((int) session.getAttribute("userId")));
		return "/back/personInfo";
	}

	// 更新用户个人的信息
	@RequestMapping(value = "/back/user/savePerson", method = { RequestMethod.POST })
	@ResponseBody
	public String updateUser(User user, HttpSession session) {
		if (user.getUserId() == null) {
			user.setUserId((int) session.getAttribute("userId"));
		} else {
			return "failure";
		}
		int result = userService.updateUser(user);
		if (result == 1) {
			return "success";
		} else {
			return "failure";
		}
	}

	// 进入（修改/添加）成果界面,复制一份module，为了防止出现更改已通过的操作。返回的是成果编辑界面
	@RequestMapping(value = { "/back/user/achievement/modify",
			"/back/user/achievement/add" }, method = { RequestMethod.POST })
	@Transactional
	public String modifyAchievement(Integer achId, Model model) {
		// 获得成果的基本信息
		Achievement achievement = achievementService.getAchiByAchId(achId);
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
		// 意见列表如果通过的成果会把意见默认为通过。
		model.addAttribute("audits", auditMapper.selectAudit(achId));
		model.addAttribute("achievement", achievement);
		return "/back/user/achievement";
	}

	// 保存成果。为了防止多次保存造成同步问题，需要进行saveCount的次数保存
	@RequestMapping(value = "/back/user/achievement/save", method = { RequestMethod.POST })
	@Transactional
	public String updateModAchievement(Model model,
			@RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement, List<Integer> moduleIds) {
		String imagePath = null;
		String videoPath = null;
		// 第一次保存
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
		} else {
			Achievement oldAchievement = achievementService
					.getAchiByAchId(achievement.getAchId());

			if (image != null) {
				imagePath = achievementService.saveImagine(image, request);
				achievement.setAchImagePath(imagePath);
			}
			if (video != null) {
				videoPath = achievementService.saveVideo(video, request);
				achievement.setAchVideoPath(videoPath);
			}

			// 更新待审核,删除之前新建的module
			if (oldAchievement.getAchStatus() == 0) {
				achievementService.updateAchievement(achievement);
				moduleService.deleteModule(moduleIds);
			}
			// 更新审核通过
			else if (oldAchievement.getAchStatus() == 1) {
				// 插入一个新的成果
				achievementService.insertAchi(achievement);
				// 更新模块内容
				moduleService.updateModuleByachId(moduleIds,
						achievement.getAchId());
			}
			// 更新未通过,删除之前新建的module
			else {
				// 将成果变成待审核
				achievement.setAchStatus(0);
				achievementService.updateAchievement(achievement);
				moduleService.deleteModule(moduleIds);
			}
		}
		
		// 转发给另一个 映射处理
		return "forward://back/user/achievement/modify?achId="
				+ achievement.getAchId();
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
			List<Achievement> achievements2 = achievementService
					.getAchiLockCondition(
							(Integer) session.getAttribute("userId"), null,
							condition.getAchStatus(),
							condition.getAchStartTime(),
							condition.getAchEndTime(), null, null,
							condition.getAuditorName(), condition.getAchName(),
							null, 0, start, count);
			map.put("totalCount", achievementService.getCount(condition));
			map.put("achievements", achievements2);
			map.put("statue", "success");
		}
		return map;
	}

	// 在修改某个模块的时候，就会发送一个模块过去。这是一个页面
	@RequestMapping(value = "/back/user/{moduleId}", method = RequestMethod.GET)
	public String getModule(@PathVariable("moduleId") int moduleId, Model model) {
		Module module = moduleService.selectModuleByModId(moduleId);
		model.addAttribute("module", module);
		return "/back/user/module";
	}
	@RequestMapping(value = "/back/user/newModule", method = RequestMethod.GET)
	public String newModule(Model model) {
		return "/back/user/module";
	}

	// 新建保存模块的时候
	@RequestMapping(value = "/back/user/{achId}/saveModule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveModule(@PathVariable("achId") Integer achId,Module module) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		//如果是新建的
		if(module.getAchId()==null){
			module.setAchId(achId);
			moduleService.insertModule(module);
			map.put("modId", module.getModId());
			map.put("statue", "success");
		}
		//如果是更新
		else {
			moduleService.updateModule(module);
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

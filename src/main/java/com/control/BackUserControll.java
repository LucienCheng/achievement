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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@RequestMapping(value = "/back/user", method = { RequestMethod.GET, RequestMethod.POST })
	@Transactional
	public String index(HttpSession session, Model model, AchievementCondition condition) {
		condition.setAuthorId((Integer) session.getAttribute("userId"));
		List<Achievement> achievements = achievementService.getAchiCondition(condition.getAuthorId(), null,
				condition.getAchStatus(), null, null, null, null, null, null, null, 0, count);
		if (condition.getAchStatus() == 2) {
			System.out.println("测试");
			for (Achievement achievement : achievements) {
				achievement.setAudit(auditMapper.selectAudit(achievement.getAchId()));
			}
		}
		model.addAttribute("achievements", achievements);
		int totalCount = achievementService.getCount(condition);
		int totalPage = (totalCount + count - 1) / count;
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", 1);
		model.addAttribute("condition", condition);

		return "/back/user/userIndex";
	}

	// 搜索
	@RequestMapping(value = "/back/user/{start}", method = { RequestMethod.GET })
	@Transactional
	public String userSearchAchievement(HttpSession session, @PathVariable("start") int start,
			AchievementCondition condition, Model model) {
		if (condition != null && condition.getAchStartTime() != null && condition.getAchEndTime() != null) {
			if (condition.getAchStartTime().length() == 0) {
				condition.setAchStartTime(null);
			}
			if (condition.getAchEndTime().length() == 0) {
				condition.setAchEndTime(null);
			}
		}
		condition.setAuthorId((Integer) session.getAttribute("userId"));
		List<Achievement> achievements = achievementService.getAchiLockCondition(condition.getAuthorId(), null,
				condition.getAchStatus(), condition.getAchStartTime(), condition.getAchEndTime(),
				condition.getAudStartTime(), condition.getAudEndTime(), null, condition.getAchName(), null, null,
				(start - 1) * count, count);
		System.out.println(condition.getAchStatus());
		if (condition.getAchStatus() == 2) {
			System.out.println("测试");
			for (Achievement achievement : achievements) {
				achievement.setAudit(auditMapper.selectAudit(achievement.getAchId()));
			}
		}
		model.addAttribute("achievements", achievements);

		int totalCount = achievementService.getCount(condition);
		int totalPage = (totalCount + count - 1) / count;
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", start);
		model.addAttribute("condition", condition);
		return "/back/user/userIndex";
	}

	// 个人资料
	@RequestMapping(value = "/back/user/personInfo", method = { RequestMethod.GET })
	@Transactional
	public String personInfo(HttpSession session, Model model) {
		model.addAttribute("user", userService.getUserById((int) session.getAttribute("userId")));
		return "/back/personInfo";
	}

	// 更新用户个人的信息
	@RequestMapping(value = "/back/user/savePerson", method = { RequestMethod.POST })
	public String updateUser(User user, HttpSession session) {
		user.setUserId((int) session.getAttribute("userId"));
		int result = userService.updateUser(user);
		return "redirect:/back/admin/personInfo";
	}

	// 进入修改界面
	@RequestMapping(value = "/back/user/achievement/modify", method = { RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public String modifyAchievement(Integer achId, Model model) {

		// 获得模块
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		// 意见列表如果通过的成果会把意见默认为通过。
		model.addAttribute("audits", auditMapper.selectAudit(achId));
		// 获得成果的基本信息
		model.addAttribute("achievement", achievementService.getAchiByAchId(achId));
		// 如果是修改的话，直接退出返回上一级页面，这是给取消按钮使用的，因为修改成果和添加成果是同一个页面
		model.addAttribute("operator", "modify");

		return "/back/user/editAchievement";
	}

	// 进入添加成果界面，并且新建一个achievement
	@RequestMapping(value = "/back/user/achievement/add", method = { RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public String addAchievement(Model model, HttpSession session) {
		Achievement achievement = new Achievement();
		User user = new User();
		user.setUserId((Integer) session.getAttribute("userId"));
		achievement.setUser(user);
		achievementService.insertAchi(achievement);
		model.addAttribute("achievement", achievement);
		// 如果是修改的话，直接退出返回上一级页面，这是给取消按钮使用的，因为修改成果和添加成果是同一个页面
		model.addAttribute("operator", "add");
		return "/back/user/editAchievement";
	}

	
	
	// 保存成果，提供给ajax使用的
	@RequestMapping(value = "/back/user/achievement/saveAjax", method = { RequestMethod.POST })
	@Transactional
	@ResponseBody
	public Map<String, Object> Save(MultipartFile image, MultipartFile video, HttpServletRequest request,
			HttpSession session, Achievement achievement) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		User user = new User();
		user.setUserId((Integer) session.getAttribute("userId"));
		achievement.setUser(user);
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(achievement.getAchId());
		// 将修改的成果设置为0，待审核状态
		achievementService.updateAchiWithSta(achIds, 0);
		String imagePath = null;
		String videoPath = null;
		achievement.setAchDate(TimeToolService.getCurrentTime());
		if (image.getOriginalFilename().length() != 0) {
			imagePath = achievementService.saveImagine(image, request);
		}
		achievement.setAchImagePath(imagePath);
		if (video.getOriginalFilename().length() != 0) {
			videoPath = achievementService.saveVideo(video, request);
		}
		achievement.setAchVideoPath(videoPath);
		achievementService.updateAchievement(achievement);
		map.put("statue", "success");
		return map;
	}

	// 批量删除achievement
	@RequestMapping(value = "/back/user/achievement/delete", method = { RequestMethod.POST })
	@Transactional
	@ResponseBody
	public Map<String, Object> deleteAchievement(@RequestBody List<Integer> achievements) {
		System.out.println("test");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		int result = achievementService.deleteAchis(achievements);
		map.put("statue", "success");
		return map;
	}

	// 删除模块
	@RequestMapping(value = "/back/user/deleteModule", method = RequestMethod.POST)
	@Transactional
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

	// 添加新建模块直接调到编辑模块的界面
	@RequestMapping(value = "/back/user/addModule", method = RequestMethod.GET)
	public String addModule(Integer achId, Model model) {
		model.addAttribute("achId", achId);
		model.addAttribute("modOpera", "add");
		return "/back/user/module";
	}

	// 修改模块直接调到编辑模块的界面
	@RequestMapping(value = "/back/user/modifyModule", method = { RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public String modifyModule(Integer achId, Integer modId, Model model) {
		model.addAttribute("module", moduleService.selectModuleByModId(modId));
		model.addAttribute("achId", achId);
		model.addAttribute("modOpera", "modify");
		return "/back/user/module";
	}

	// 保存新建模块，传进来的是成果id和模块内容，然后返回上一层
	@RequestMapping(value = "/back/user/saveModule", method = RequestMethod.POST)
	@Transactional
	public String saveModule(Module module) {
		
		moduleService.insertModules(module);
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(module.getAchId());
		// 设置为未发布状态
		achievementService.updateAchiWithSta(achIds, -1);
		System.out.println(module);
		return "forward:/back/user/modifyModule?achId=" + module.getAchId() + "&modId=" + module.getModId();
	}

	// 保存修改模块，传进来的是成果id和模块内容，然后返回上一层
	@RequestMapping(value = "/back/user/saveModifyModule", method = RequestMethod.POST)
	@Transactional
	public String saveModifyModule(Module module) {
		moduleService.updateModules(module);
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(module.getAchId());
		// 设置为待审核
		achievementService.updateAchiWithSta(achIds, -1);
		return "forward:/back/user/modifyModule?achId=" + module.getAchId() + "&modId=" + module.getModId();
	}


	// 三级页面模块，初始页面
	@RequestMapping(value = "/back/user/modules/{achId}", method = { RequestMethod.GET })
	@Transactional
	public String getAchievementModules(@PathVariable("achId") int achId, Model model,HttpSession session,
			String Url,RedirectAttributes redirectAttributes) {
		Achievement achievement = achievementService.getAchiByAchId(achId);
		if(!((Integer)session.getAttribute("userId")).equals(achievement.getUser().getUserId())) {
			
			System.out.println((Integer)session.getAttribute("userId")+achievement.getUser().getUserId());
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

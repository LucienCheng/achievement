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
	@RequestMapping(value = "/back/user/", method = { RequestMethod.GET ,RequestMethod.POST})
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
	public String updateUser(User user,HttpSession session){
		user.setUserId((int)session.getAttribute("userId"));
		int result=userService.updateUser(user);
		return "redirect:/back/admin/personInfo";
	}

	// 进入修改界面
	@RequestMapping(value = "/back/user/achievement/modify", method = { RequestMethod.POST })
	@Transactional
	public String modifyAchievement(Integer achId, Model model) {

		// 获得模块
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		// 意见列表如果通过的成果会把意见默认为通过。
		model.addAttribute("audits", auditMapper.selectAudit(achId));
		// 获得成果的基本信息
		model.addAttribute("achievement",
				achievementService.getAchiByAchId(achId));
		return "/back/user/modifyAchievement";
	}

	// 进入添加成果界面，并且新建一个achievement
	@RequestMapping(value = "/back/user/achievement/add", method = { RequestMethod.POST })
	@Transactional
	public String addAchievement(Model model) {
		Achievement achievement=new Achievement();
		achievementService.insertAchi(achievement);
		model.addAttribute("achId", achievement.getAchId());
		return "/back/user/addAchievement";
	}

	// 保存修改的成果。这种效果就是一旦保存了模块，那就生效了。
	@RequestMapping(value = "/back/user/achievement/modifySave", method = { RequestMethod.POST })
	@Transactional
	public String modifySave(Model model, @RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement, List<Integer> oldModules,
			List<Integer> newModules) {
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(achievement.getAchId());
		// 将修改的成果设置为0，待审核状态
		achievementService.updateAchiWithSta(achIds, 0);
		if(achievement.getAchStatus()==1){
			achievementService.updateAchiWithModify(achIds, 1);
		}
		String imagePath = null;
		String videoPath = null;
		achievement.setAchDate(TimeToolService.getCurrentTime());
		if (image != null) {
			imagePath = achievementService.saveImagine(image, request);
			achievement.setAchImagePath(imagePath);
		}
		if (video != null) {
			videoPath = achievementService.saveVideo(video, request);
			achievement.setAchVideoPath(videoPath);
		}
		achievementService.updateAchievement(achievement);
		// 保存退出
		return "forward:/back/user/achievement/";
	}
//新建的时候的保存
	@RequestMapping(value = "/back/user/achievement/addSave", method = { RequestMethod.POST })
	@Transactional
	public String addSave(Model model, @RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement) {
		String imagePath = null;
		String videoPath = null;
		achievement.setAchDate(TimeToolService.getCurrentTime());
		imagePath = achievementService.saveImagine(image, request);
		videoPath = achievementService.saveVideo(video, request);
		achievement.setAchImagePath(imagePath);
		achievement.setAchVideoPath(videoPath);
		//设置为待审核
		achievement.setAchStatus(0);
		//一个成果插入
		int achiResult = achievementService.insertAchi(achievement);
		// 保存退出
				return "forward:/back/user/achievement/";
	}

	// 批量删除achievement
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
	//添加新建模块直接调到编辑模块的界面
	@RequestMapping(value = "/back/user/addModule", method = RequestMethod.POST)
	public String addModule(Integer achId,Model model) {
		model.addAttribute("achId", achId);
		return "/back/user/module";
	}
	//保存新建模块，传进来的是成果id和模块内容，然后返回上一层
		@RequestMapping(value = "/back/user/saveModule", method = RequestMethod.POST)
		public String saveModule(Module module) {
			moduleService.insertModules(module);
			List<Integer> achIds=new ArrayList<Integer>();
			achIds.add(module.getAchId());
			//设置为未发布状态
			achievementService.updateAchiWithSta(achIds, -1);
			return "redirect:/back/user/achievement/modify?achId="+module.getAchId();
		}
		
		//修改模块直接调到编辑模块的界面
		@RequestMapping(value = "/back/user/modifyModule", method = RequestMethod.POST)
		public String modifyModule(Integer modId,Model model) {
			model.addAttribute("module", moduleService.selectModuleByModId(modId));
			return "/back/user/module";
		}
		//保存修改模块，传进来的是成果id和模块内容，然后返回上一层
			@RequestMapping(value = "/back/user/saveModifyModule", method = RequestMethod.POST)
			public String saveModifyModule(Module module) {
				moduleService.updateModules(module);
				List<Integer> achIds=new ArrayList<Integer>();
				achIds.add(module.getAchId());
				//设置为待审核
				achievementService.updateAchiWithSta(achIds, -1);
				return "redirect:/back/user/achievement/modify?achId="+module.getAchId();
			}

}

package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.reflect.UnlockSignature;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.AuditMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Audit;
import com.entity.User;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;
import com.service.UserService;

//审核人员控制区域
@Controller
public class BackAuditorControl {
	@Resource
	private ModuleService moduleService;
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource
	private AuditMapper auditMapper;
	@Resource(name = "UserServiceImpl")
	private UserService userService;
	private final int count = 5;// 显示的条数

	// 个人资料
	@RequestMapping(value = "/back/auditor/personInfo", method = { RequestMethod.GET })
	public String personInfo(HttpSession session, Model model) {
		model.addAttribute("user",
				userService.getUserById((int) session.getAttribute("userId")));
		return "/back/personInfo";
	}

	// 更新用户个人的信息
	@RequestMapping(value = "/back/auditor/savePerson", method = { RequestMethod.POST })
	public String updateUser(User user, HttpSession session) {
		user.setUserId((int) session.getAttribute("userId"));
		int result = userService.updateUser(user);
		return "redirect:/back/admin/personInfo";
	}

	// 审核人员的首页，显示待审核的界面

	@RequestMapping(value = { "/back/auditor/{start}" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	@Transactional
	public String Index(Model model, AchievementCondition achievementCondition,
			HttpSession session, @PathVariable("start") Integer start) {
		if (achievementCondition != null
				&& achievementCondition.getAchStartTime() != null
				&& achievementCondition.getAchEndTime() != null) {
			if (achievementCondition.getAchStartTime().length() == 0) {
				achievementCondition.setAchStartTime(null);
			}
			if (achievementCondition.getAchEndTime().length() == 0) {
				achievementCondition.setAchEndTime(null);
			}
		}
		List<Integer> achIds = (List<Integer>) session.getAttribute("achIds");
		// 获取achids进行释放锁
		if (achIds != null && achIds.size() != 0) {
			System.out.println(achIds);
			achievementService.updateAchiWithLock(achIds, 0);
			achIds.clear();

		} else {
			achIds = new ArrayList<Integer>();
		}

		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null,
						(int) session.getAttribute("userId"), 0,
						achievementCondition.getAchStartTime(),
						achievementCondition.getAchEndTime(), null, null, null,
						achievementCondition.getAchName(),
						achievementCondition.getAuthorName(), 0, (start - 1)
								* count, count);
		achievementCondition.setAchStatus(0);
		achievementCondition.setAuditorId((int) session.getAttribute("userId"));
		achievementCondition.setAchLock("0");
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		int totalCount = achievementService.getCount(achievementCondition);
		int totalPage = (totalCount + count - 1) / count;
		model.addAttribute("totalCount", totalCount);
		// 加锁
		// 一旦用户失效就会释放锁
		if (achIds.size() > 0) {
			achievementService.updateAchiWithLock(achIds, 1);
			session.setAttribute("achIds", achIds);
		}
		model.addAttribute("achievements", achievements);
		model.addAttribute("curPage", start);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("condition", achievementCondition);
		return "/back/auditor/auditorIndex";
	}

	// 经过审核的界面
	@RequestMapping(value = { "/back/auditor/audited/{start}" }, method = { RequestMethod.GET })
	public String pass(Model model, AchievementCondition condition,
			@PathVariable("start") int start, HttpSession session) {
		if (condition != null && condition.getAchStartTime() != null
				&& condition.getAchEndTime() != null) {
			if (condition.getAchStartTime().length() == 0) {
				condition.setAchStartTime(null);
			}
			if (condition.getAchEndTime().length() == 0) {
				condition.setAchEndTime(null);
			}
		}
		if (condition.getAchStatus() == 2 || condition.getAchStatus() == 1) {
			List<Achievement> achievements = achievementService
					.getAchiLockCondition(null,
							(Integer) session.getAttribute("userId"),
							condition.getAchStatus(), null, null,
							condition.getAudStartTime(),
							condition.getAudEndTime(), null,
							condition.getAchName(), condition.getAuthorName(),
							0, (start - 1) * count, count);
			condition.setAuditorId((int) session.getAttribute("userId"));
			condition.setAchLock("0");
			int totalCount = achievementService.getCount(condition);
			int totalPage = (totalCount + count - 1) / count;
			model.addAttribute("achievements", achievements);
			model.addAttribute("curPage", start);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("condition", condition);
			return "/back/auditor/auditorIndex";
		} else {
			return "redirect:/back/auditor/1";
		}
	}

	// 这是一个动作，将成果变成审核通过,返回待审核成果给待审核界面
	@RequestMapping(value = "/back/auditor/pass", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> passAchievement(
			@RequestBody List<Integer> oldAchIds, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		// 设置状态为1，通过状态
		achievementService.updateAchiWithSta(oldAchIds, 1);
		// 插入通过记录
		auditMapper.insertAudits(oldAchIds, TimeToolService.getCurrentTime(),
				(int) session.getAttribute("userId"));
		// 解锁
		achievementService.updateAchiWithLock(oldAchIds, 0);
		return map;

	}

	// 这是一个动作，将成果变成审核未通过，返回待审核界面
	@RequestMapping(value = "/back/auditor/unpass", method = { RequestMethod.POST })
	@Transactional
	public String passAchievement(Audit audit, HttpSession session) {
		audit.setUserId((int) session.getAttribute("userId"));
		audit.setAudDate(TimeToolService.getCurrentTime());
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(audit.getAchId());
		// 设置为未通过。
		System.out.println(audit);
		achievementService.updateAchiWithSta(achIds, 2);
		auditMapper.insertAudit(audit);
		// 解锁
		achievementService.updateAchiWithLock(achIds, 0);
		return "redirect:/back/auditor/1";
	}

	// 预览成果界面
	@RequestMapping(value = "/back/auditor/{achievementId}/video", method = { RequestMethod.GET })
	@Transactional
	public String getAchievementVideo(
			@PathVariable("achievementId") int achievementId, Model model) {
		Achievement achievement = achievementService
				.getAchiByAchId(achievementId);
		model.addAttribute("video", achievement.getAchVideoPath());
		model.addAttribute("achId", achievementId);
		achievement.setAchCTR(achievement.getAchCTR() + 1);
		achievementService.updateAchievement(achievement);
		return "/front/video";
	}

	// 三级页面模块，初始页面
	@RequestMapping(value = "/back/auditor/modules/{achId}", method = { RequestMethod.GET })
	public String getAchievementModules(@PathVariable("achId") int achId,
			Model model) {
		Achievement achievement = achievementService.getAchiByAchId(achId);
		model.addAttribute("modules", moduleService.selectModuleByAchId(achId));
		model.addAttribute("achievement", achievement);
		return "/front/modules";
	}

}

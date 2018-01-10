package com.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.AuditMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Audit;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;
import com.service.UserService;

@Controller
public class BackAuditorControl {
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource
	private AuditMapper auditMapper;
	@Resource(name="UserServiceImpl")
	private UserService userService;
	private final int count = 10;// 显示的条数

	// 审核人员的首页，显示待审核的界面
	@RequestMapping(value = "/back/auditor", method = { RequestMethod.GET })
	public String Index(Model model, AchievementCondition achievementCondition) {
		List<Integer> achIds = new ArrayList<Integer>();
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null,
						achievementCondition.getAuditorId(), 0,
						achievementCondition.getAchStartTime(),
						achievementCondition.getAchEndTime(), null, null, null,
						achievementCondition.getAchName(),
						achievementCondition.getAuthorName(), 0, 0, count);
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		//加锁
		achievementService.updateAchiWithLock(achIds, 1);
		model.addAttribute("totalCount",
				achievementService.getCount(achievementCondition));
		model.addAttribute("achievements", achievements);
		return "/back/admin/auditor/auditorIndex";
	}

	@RequestMapping(value = "/back/auditor/pass", method = { RequestMethod.GET })
	public String pass(Model model, AchievementCondition condition) {
		// 这里会返回待审核列表给前端
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null, condition.getAuditorId(), 1,
						condition.getAchStartTime(), condition.getAchEndTime(),
						null, null, null, condition.getAchName(),
						condition.getAuthorName(), 0, 0, count);
		model.addAttribute("totalCount", achievementService.getCount(condition));
		model.addAttribute("achievements", achievements);
		return "/back/admin/auditor/audited";
	}

	@RequestMapping(value = "/back/auditor/unPass", method = { RequestMethod.GET })
	public String unPass(Model model, AchievementCondition condition) {
		// 这里会返回待审核列表给前端
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null, condition.getAuditorId(), 2,
						condition.getAchStartTime(), condition.getAchEndTime(),
						null, null, null, condition.getAchName(),
						condition.getAuthorName(), 0, 0, count);
		model.addAttribute("totalCount", achievementService.getCount(condition));
		model.addAttribute("achievements", achievements);
		return "/back/admin/auditor/audited";
	}
	//个人资料
			@RequestMapping(value="/back/admin/personInfo",method={RequestMethod.GET})
			public String personInfo(HttpSession session,Model model) {
				model.addAttribute("user", userService.getUserById((int)session.getAttribute("userId")));
				return "/back/personInfo";
			}
	// 审核通过,返回待审核成果给待审核界面
	@RequestMapping(value = "/back/auditor/achievement/pass/{start}", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> passAchievement(List<Integer> achIds,
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		// 设置状态为1，通过状态
		achievementService.updateAchiWithSta(achIds, 1);
		// 解锁
		achievementService.updateAchiWithLock(achIds, 0);
		// 这里会返回待审核列表给前端
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null, condition.getAuditorId(), 1,
						condition.getAchStartTime(), condition.getAchEndTime(),
						null, null, null, condition.getAchName(),
						condition.getAuthorName(), 0, start, count);
		achIds.clear();
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		// 锁定
		achievementService.updateAchiWithLock(achIds, 1);
		map.put("achievements", achievements);
		return map;

	}

	// 审核未通过，返回待审核界面
	@RequestMapping(value = "/back/auditor/achievement/unpass/{start}", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> passAchievement(Audit audit,
			AchievementCondition condition, @PathVariable("start") int start) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		audit.setAudDate(TimeToolService.getCurrentTime());
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(audit.getAchId());
		// 设置为未通过。
		achievementService.updateAchiWithSta(achIds, 2);
		auditMapper.insertAudit(audit);
		// 解锁
		achievementService.updateAchiWithLock(achIds, 0);
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null, condition.getAuditorId(), 0,
						condition.getAchStartTime(), condition.getAchEndTime(),
						null, null, null, condition.getAchName(),
						condition.getAuthorName(), 0, start, count);
		achIds.clear();
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		// 重新获取的成果进行锁定
		achievementService.updateAchiWithLock(achIds, 1);
		map.put("achievements", achievements);
		return map;
	}

	// 返回检索的列表，这里包括了未通过的和已经通过的页面。
	@RequestMapping(value = "/back/auditor/achievement/audited/search/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> auditorSearchAuditedAchievement(
			@PathVariable("start") int start, AchievementCondition condition,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null,
						(Integer) session.getAttribute("userId"),
						condition.getAchStatus(), null, null,
						condition.getAudStartTime(), condition.getAudEndTime(),
						null, condition.getAchName(),
						condition.getAuthorName(), 0, start, count);
		map.put("achievements", achievements);
		map.put("statue", "success");
		return map;
	}

	// 这是待审核的搜索页面，需要加锁
	@RequestMapping(value = "/back/auditor/achievement/unAudited/search/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> auditorSearchUnauditedAchievement(
			@PathVariable("start") int start, AchievementCondition condition,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		List<Integer> achIds = new ArrayList<Integer>();
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null,
						(Integer) session.getAttribute("userId"),
						condition.getAchStatus(), condition.getAchStartTime(),
						condition.getAchEndTime(), null, null, null,
						condition.getAchName(), condition.getAuthorName(), 0,
						start, count);
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		// 加锁
		achievementService.updateAchiWithLock(achIds, 1);
		map.put("achievements", achievements);
		map.put("statue", "success");
		return map;
	}
}

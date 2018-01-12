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
import com.entity.User;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;
import com.service.UserService;
//审核人员控制区域
@Controller
public class BackAuditorControl {
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource
	private AuditMapper auditMapper;
	@Resource(name="UserServiceImpl")
	private UserService userService;
	private final int count = 10;// 显示的条数
	// 个人资料
		@RequestMapping(value = "/back/auditor/personInfo", method = { RequestMethod.GET })
		public String personInfo(HttpSession session, Model model) {
			model.addAttribute("user",
					userService.getUserById((int) session.getAttribute("userId")));
			return "/back/personInfo";
		}
	//更新用户个人的信息
	@RequestMapping(value="/back/auditor/savePerson",method={RequestMethod.POST})
	@ResponseBody
	public String updateUser(User user,HttpSession session){
		if(user.getUserId()==null){
			user.setUserId((int)session.getAttribute("userId"));
		}
		else {
			return "failure";
		}
		int result=userService.updateUser(user);
		if (result==1) {
			return "success";
		}
		else {
			return "failure";
		}
	}
	// 审核人员的首页，显示待审核的界面
	@RequestMapping(value = "/back/auditor", method = { RequestMethod.GET ,RequestMethod.POST})
	@Transactional
	public String Index(Model model, AchievementCondition achievementCondition,HttpSession session) {
		List<Integer> achIds = new ArrayList<Integer>();
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null,
						(int)session.getAttribute("userId"), 0,
						achievementCondition.getAchStartTime(),
						achievementCondition.getAchEndTime(), null, null, null,
						achievementCondition.getAchName(),
						achievementCondition.getAuthorName(), 0, 0, count);
		for (Achievement achievement : achievements) {
			achIds.add(achievement.getAchId());
		}
		System.out.println(achIds);
		//加锁
		//一旦用户失效就会释放锁
		if (achIds.size()>0) {
			achievementService.updateAchiWithLock(achIds, 1);
			session.setAttribute("achIds", achIds);
		}
		
		achievementCondition.setAchStatus(0);
		model.addAttribute("totalCount",
				achievementService.getCount(achievementCondition));
		model.addAttribute("achievements", achievements);
		return "/back/auditor/auditorIndex";
	}

	// 这是待审核的搜索页面，需要加锁,也就是auditorindex使用的ajax
	@RequestMapping(value = "/back/auditor/unAudited/{start}", method = { RequestMethod.GET })
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
		//一旦用户失效就会释放锁
		if (achIds.size()>0) {
			achievementService.updateAchiWithLock(achIds, 1);
			session.setAttribute("achIds", achIds);
		}
		map.put("achievements", achievements);
		map.put("statue", "success");
		return map;
	}
	
	//也就是auditorindex使用的ajax
	@RequestMapping(value = {"/back/auditor/audited"}, method = { RequestMethod.GET })
	public String pass(Model model, AchievementCondition condition) {
		// 这里会返回待审核列表给前端
		if(condition.getAchStatus()==2||condition.getAchStatus()==0){
		List<Achievement> achievements = achievementService
				.getAchiLockCondition(null, condition.getAuditorId(), condition.getAchStatus(),
						condition.getAchStartTime(), condition.getAchEndTime(),
						null, null, null, condition.getAchName(),
						condition.getAuthorName(), 0, 0, count);
		model.addAttribute("totalCount", achievementService.getCount(condition));
		model.addAttribute("achievements", achievements);
		}
		
		return "/back/auditor/audited";
	}

	// 返回检索的列表，这里包括了未通过的和已经通过的页面。也就是已经审核过的
	@RequestMapping(value = "/back/auditor/audited/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> auditorSearchAuditedAchievement(
			@PathVariable("start") int start, AchievementCondition condition,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		if(condition.getAchStatus()==2||condition.getAchStatus()==0){
			List<Achievement> achievements = achievementService
					.getAchiLockCondition(null,
							(Integer) session.getAttribute("userId"),
							condition.getAchStatus(), null, null,
							condition.getAudStartTime(), condition.getAudEndTime(),
							null, condition.getAchName(),
							condition.getAuthorName(), 0, start, count);
			map.put("achievements", achievements);
			map.put("statue", "success");
		}
		
		return map;
	}
	// 这是一个动作，将成果变成审核通过,返回待审核成果给待审核界面
	@RequestMapping(value = "/back/auditor/pass/{start}", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> passAchievement(List<Integer> achIds,HttpSession session,
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		// 设置状态为1，通过状态
		achievementService.updateAchiWithSta(achIds, 1);
		achievementService.updateAchiWithModify(achIds, 0);
		//插入通过记录
		auditMapper.insertAudits(achIds, TimeToolService.getCurrentTime(), (int)session.getAttribute("userId"));
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
		//一旦用户失效就会释放锁
		if (achIds.size()>0) {
			achievementService.updateAchiWithLock(achIds, 1);
			session.setAttribute("achIds", achIds);
		}
		map.put("totalCount",
				achievementService.getCount(condition));
		map.put("achievements", achievements);
		return map;

	}

	// 这是一个动作，将成果变成审核未通过，返回待审核界面
	@RequestMapping(value = "/back/auditor/unpass/{start}", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> passAchievement(Audit audit,HttpSession session,
			AchievementCondition condition, @PathVariable("start") int start) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		audit.setUserId((int)session.getAttribute("userId"));
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
		//一旦用户失效就会释放锁
		if (achIds.size()>0) {
			achievementService.updateAchiWithLock(achIds, 1);
			session.setAttribute("achIds", achIds);
		}
		map.put("totalCount",
				achievementService.getCount(condition));
		map.put("achievements", achievements);
		return map;
	}


}

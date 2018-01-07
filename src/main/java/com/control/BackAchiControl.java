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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dao.AuditMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Audit;
import com.entity.Module;
import com.service.AchievementService;
import com.service.ModuleService;
import com.service.TimeToolService;

@Controller
public class BackAchiControl {
	@Resource(name = "AchievementImpl")
	private AchievementService achievementService;
	@Resource(name = "moduleImpl")
	private ModuleService moduleService;
	@Resource
	private AuditMapper auditMapper;
	private final int count = 10;// 显示的条数

	// 这里操作是为了新创建一份module在记录中，如果保存成果，就会使用，不保存，那就删除原来的module
	@RequestMapping(value = "/back/user/modifyAchievement", method = { RequestMethod.POST })
	@Transactional
	public String modifyAchievement(int achId, Model model) {
		List<Integer> oldModuleIds = new ArrayList<Integer>();
		// 通过搜索成果的id获得一份module。
		List<Module> modules = moduleService.selectModuleByAchId(achId);
		for (Module module : modules) {
			oldModuleIds.add(module.getModId());
		}
		// 获得成果的基本信息
		Achievement achievement = achievementService.getAchiByAchId(achId);
		// 重新拷贝一份，重新把插入的主键重置给modId属性
		int result = moduleService.insertModules(modules);
		List<Integer> newModuleIds = new ArrayList<Integer>();
		// 将复制一份的module进行复制。
		for (Module module : modules) {
			newModuleIds.add(module.getModId());
		}
		model.addAttribute("achievement", achievement);
		model.addAttribute("newModuleIds", newModuleIds);
		model.addAttribute("oldModuleIds", oldModuleIds);
		return "/back/user/modifyAchievement";
	}

	// 保存成果的操作,其中包括了保存一个成果，更改了模块的id.judge这个是判断是修改了，还是没有修改
	@RequestMapping(value = "/back/user/updateModAchievement/{judge}", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> updateModAchievement(
			@RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement, List<Integer> moduleIds,
			@PathVariable("judge") int judge) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 默认是失败的
		map.put("statue", "failure");
		// 修改了
		if (judge == 1) {

			if (image != null) {
				String imagePath = achievementService.saveImagine(image,
						request);
				achievement.setAchImagePath(imagePath);
				map.put("image", imagePath);
			}
			if (video != null) {
				String videoPath = achievementService.saveVideo(video, request);
				achievement.setAchVideoPath(videoPath);
				map.put("video", videoPath);
			}
			//使用的是修改的更新
			boolean achiResult = achievementService.updateAchiModify(achievement);
			//更新模块内容
			int modResult = moduleService.updateModuleByachId(moduleIds,
					achievement.getAchId());
			// 操作成功的时候就会产生相应的返回结果
			if (achiResult && modResult > 0) {
				map.put("statue", "success");
			}
		}
		// 没有修改
		else {
			moduleService.deleteModule(moduleIds);
		}
		return map;
	}

	// 删除achievement
	@RequestMapping(value = "/back/user/deleteAchievement/{start}", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deleteAchievement(List<Integer> achievements,AchievementCondition condition,
			@PathVariable("start")int start, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = achievementService.deleteAchis(achievements);

		if (result > 0) {
			map.put("statue", "success");
			List<Achievement> achievements2 = achievementService.getAchiCondition(condition.getAuthorId(), 
					condition.getAuditorId(), condition.getAchStatus(),
					condition.getAchStartTime(), condition.getAchEndTime(), 
					condition.getAudStartTime(), condition.getAudEndTime(), 
					condition.getAuditorName(), condition.getAchName(), condition.getAuthorName(), start, count);
			map.put("achievements", achievements2);
		} else {
			map.put("statue", "failure");
		}
		return map;
	}

	// 新建一个成果,并且保存,如果出现刚刚保存，然后通过了，这时需要判断一下状态
	@RequestMapping(value = "/back/user/newAchievement", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> saveAchievement(
			@RequestParam MultipartFile image,
			@RequestParam MultipartFile video, HttpServletRequest request,
			Achievement achievement, List<Integer> moduleIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		//初始的状态
		if(achievement.getAchStatus()==null){
			
			String imagePath = achievementService.saveImagine(image, request);
			String videoPath = achievementService.saveVideo(video, request);
			achievement.setAchImagePath(imagePath);
			achievement.setAchVideoPath(videoPath);
			int achiResult = achievementService.insertAchi(achievement);
			int modResult = moduleService.updateModuleByachId(moduleIds,
					achievement.getAchId());
			if (achiResult > 0 && modResult > 0) {
				map.put("image", imagePath);
				map.put("video", videoPath);
				map.put("statue", "success");
			}
		}
		//如果状态变化了
		else {
			map.put("message", "操作失败，重新关闭页面人，然后再操作");
		}
		
		return map;
	}

	// 审核通过
	@RequestMapping(value = "/back/auditor/passAchievement/{start}", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> passAchievement(List<Integer> achIds,
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		achievementService.updateAchiWithSta(achIds, 1);
		
		map.put("achievements", achievementService.getAchiCondition(condition.getAuthorId(), 
				condition.getAuditorId(), condition.getAchStatus(),
				condition.getAchStartTime(), condition.getAchEndTime(), 
				condition.getAudStartTime(), condition.getAudEndTime(), 
				condition.getAuditorName(), condition.getAchName(), condition.getAuthorName(), start, count));
		return map;

	}

	// 审核未通过
	@RequestMapping(value = "/back/auditor/unpassAchievement/{start}", method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public Map<String, Object> passAchievement(Audit audit,AchievementCondition condition,
			@PathVariable("start") int start) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		audit.setAudDate(TimeToolService.getCurrentTime());
		List<Integer> achIds = new ArrayList<Integer>();
		achIds.add(audit.getAchId());
		// 设置为未通过。
		achievementService.updateAchiWithSta(achIds, 2);
		auditMapper.insertAudit(audit);
		map.put("achievements", achievementService.getAchiCondition(condition.getAuthorId(), 
				condition.getAuditorId(), condition.getAchStatus(),
				condition.getAchStartTime(), condition.getAchEndTime(), 
				condition.getAudStartTime(), condition.getAudEndTime(), 
				condition.getAuditorName(), condition.getAchName(), condition.getAuthorName(), start, count));
		map.put("statue", "success");
		return map;

	}

	// 一下是检索一个achievement列表，后台检索
	@RequestMapping(value = "/back/searchAchievement/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> BackSearchAchievement(
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		map.put("achievements", achievementService.getAchiCondition(condition.getAuthorId(), 
				condition.getAuditorId(), condition.getAchStatus(),
				condition.getAchStartTime(), condition.getAchEndTime(), 
				condition.getAudStartTime(), condition.getAudEndTime(), 
				condition.getAuditorName(), condition.getAchName(), condition.getAuthorName(), start, count));
		map.put("statue", "success");
		return map;
	}
//前台检索
	@RequestMapping(value = "/front/searchAchievement/{start}", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> frontSearchAchievement(
			@PathVariable("start") int start, AchievementCondition condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statue", "failure");
		map.put("achievements", achievementService.getAchiCondition(condition.getAuthorId(), 
				condition.getAuditorId(), condition.getAchStatus(),
				condition.getAchStartTime(), condition.getAchEndTime(), 
				condition.getAudStartTime(), condition.getAudEndTime(), 
				condition.getAuditorName(), condition.getAchName(), condition.getAuthorName(), start, count));
		map.put("statue", "success");
		return map;
	}
}

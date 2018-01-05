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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Achievement;
import com.entity.Module;
import com.service.AchievementService;
import com.service.ModuleService;

@Controller
@RequestMapping("/user")
public class BackAchiControl {
	@Resource(name="AchievementImpl")
	private AchievementService achievementService;
	@Resource(name="moduleImpl")
	private ModuleService moduleService;
	private final int count=10;
	//这里操作是为了新创建一份module在记录中，如果保存，就会使用，不保存，那就删除原来的module
	@RequestMapping(value="/back/user/modifyAchievement",method={RequestMethod.POST} )
	@Transactional
	public String updateAchievement(int achId,Model model){
		List<Module> modules=moduleService.selectModuleByAchId(achId);
		Achievement achievement=achievementService.getAchiByAchId(achId);
		int result=moduleService.insertModules(modules);
		List<Integer> moduleIds=new ArrayList<Integer>();
		for(int i=0;i<modules.size();i++){
			moduleIds.add(modules.get(i).getModId());
		}
		model.addAttribute("achievement", achievement);
		model.addAttribute("moduleIds", moduleIds);
		moduleIds=null;
		modules=null;
		return "/back/user/modifyAchievement";
	}
	//保存成果的操作,其中包括了保存一个成果，更改了模块的id.
	@RequestMapping(value="/back/user/updateAchievement",method={RequestMethod.POST} )
	@ResponseBody
	public Map<String, Object> updateAchievement(@RequestParam MultipartFile image,
			@RequestParam MultipartFile video,HttpServletRequest request,Achievement achievement,
			List<Integer> moduleIds){
		Map<String, Object> map=new HashMap<String, Object>();
		if(image!=null){
			String imagePath=achievementService.saveImagine(image,request);
			achievement.setAchImagePath(imagePath);
			map.put("image", imagePath);
		}
		if(video!=null){
			String videoPath=achievementService.saveVideo(video,request);
			achievement.setAchVideoPath(videoPath);
			map.put("video", videoPath);
		}
		int achiResult=achievementService.insertAchi(achievement);
		int modResult=moduleService.updateModuleByachId(moduleIds, achievement.getAchId());
		if(achiResult>0&&modResult>0){
			map.put("statue", "success");
		}
		else {
			map.put("statue", "failure");
		}
		return map;
	}
	//删除achievement
	@RequestMapping(value="/back/user/deleteAchievement",method={RequestMethod.POST} )
	@ResponseBody
	public Map<String, Object> deleteAchievement(List<Integer> achievements,int start,
			HttpSession session){
		Map<String, Object> map=new HashMap<String, Object>();
		int result=achievementService.deleteAchis(achievements);
		
		if (result>0) {
			map.put("statue", "success");
			List<Achievement> achievements2=achievementService.getAchiByUserId((Integer)session.getAttribute("userId"), start, count);
			map.put("achievements", achievements2);
		}
		else {
			map.put("statue", "failure");
		}
		return map;
	}	
	//更新成果的操作,重新创建一个
	@RequestMapping(value="/back/user/updateAchievement",method={RequestMethod.POST} )
	@ResponseBody
	@Transactional
	public Map<String, Object> saveAchievement(@RequestParam MultipartFile image,
			@RequestParam MultipartFile video,HttpServletRequest request,Achievement achievement,
			List<Integer> moduleIds){
		Map<String, Object> map=new HashMap<String, Object>();
		String imagePath=achievementService.saveImagine(image,request);
		String videoPath=achievementService.saveVideo(video,request);
		achievement.setAchImagePath(imagePath);
		achievement.setAchVideoPath(videoPath);
		int achiResult=achievementService.insertAchi(achievement);
		int modResult=moduleService.updateModuleByachId(moduleIds, achievement.getAchId());
		if(achiResult>0&&modResult>0){
			map.put("image", imagePath);
			map.put("video", videoPath);
			map.put("statue", "success");
		}
		else {
			map.put("statue", "failure");
		}
		return map;
	}
	
	
		
}

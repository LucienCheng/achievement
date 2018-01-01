package com.control;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.examples.CreateCell;
import org.apache.poi.xssf.usermodel.examples.CreateUserDefinedDataFormats;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Role;
import com.entity.TestJson;
import com.entity.User;
import com.service.AchievementService;
import com.service.UserService;


@Controller
@RequestMapping("/ajax/")
public class TestControlAjax {
	private User CreateUser(){
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		return (User) context.getBean("user");
		
	}
	/*@Resource(name="AchievementImpl")
	private AchievementService achievementService;
	@Resource(name="userServiceImpl")
	private UserService userService;
	@RequestMapping(value="test",method={RequestMethod.GET,RequestMethod.POST} )
	@ResponseBody
	public Map<String, Object> test(@RequestParam MultipartFile image,HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("image", achievementService.saveImagine(image,request));
		return map;
	}	
	@RequestMapping(value="dividePage/{pageNum}",method={RequestMethod.GET,RequestMethod.POST} )
	//@ResponseBody
	public void dividePage(@PathVariable Integer pageNum,HttpServletRequest request,
			ModelAndView modelAndView){
		System.out.println(request.getRequestURI());
		Map<String, Object> map=new HashMap<String, Object>();
		List<User> users=userService.getUserByConditon(null, null, pageNum*3, 3);
		int total=userService.getUserCount();
		int pageSize=users.size();
		int totalPage = (total + 3-1)/ 3;
		map.put("total", total);
		map.put("pageSize", pageSize);
		map.put("totalPage", totalPage);
		map.put("users", users);
		modelAndView.setViewName("dividePage");
	}
	*/
	@RequestMapping(value="test",method={RequestMethod.GET,RequestMethod.POST} )
	@ResponseBody
	public Map<String, Object> test(){
		
		 User user=CreateUser();
		System.out.println(user.hashCode());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("test", "haha");
		return map;
	}	
	@RequestMapping(value="testJson",method={RequestMethod.GET,RequestMethod.POST} )
	@ResponseBody
	public Map<String, Object> testJson(@RequestBody List<TestJson> testJsons){
		System.out.println(testJsons);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("test", "haha");
		return map;
	}	
	@RequestMapping(value="testForm",method={RequestMethod.GET,RequestMethod.POST} )
	public String testForm( @ModelAttribute TestJson testJson){
		System.out.println(testJson);
		return "test";
	}
	@RequestMapping(value="testAdd",method={RequestMethod.GET,RequestMethod.POST} )
	public String testAdd( @Valid TestJson testJson,BindingResult bin){
		System.out.println(testJson);
		if(bin.hasErrors()){
			System.out.println(bin.getErrorCount());
			return "test";
		}
		System.out.println("haha");
		return "test";
	}
}

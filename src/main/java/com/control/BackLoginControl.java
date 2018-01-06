package com.control;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;

@Controller
public class BackLoginControl {
	@Resource(name="userServiceImpl")
	private UserService userService;
	@RequestMapping(value="/back",method={RequestMethod.GET})
	public String back(){
		return "/back/login";
		
	}
	
	@RequestMapping(value="/back/login",method={RequestMethod.POST})
	public String vertifyLogin(User user){
		boolean result=userService.vertifyUser(user);
		if(result){
			Role role=user.getRole();
			switch (role.getRoleId()) {
			case 1:
				return "/back/adminIndex";
			case 2:
				return "/back/auditor/auditorIndex";
			case 3:
				return "/back/admin/adminIndex";
			}
		}
			return "/back/login";
		
	}
	@RequestMapping(value="/back/loginOut",method={RequestMethod.POST})
	public String loginOut(int userId){
		return "/back/login";
		
	}
}
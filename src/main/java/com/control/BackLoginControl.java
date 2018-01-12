package com.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Role;
import com.entity.User;
import com.service.UserService;

@Controller
public class BackLoginControl {
	@Resource(name="UserServiceImpl")
	private UserService userService;
	@RequestMapping(value="/back",method={RequestMethod.GET})
	public String back(){
		return "/back/login";
	}
	@RequestMapping(value="/back/login",method={RequestMethod.POST})
	public String vertifyLogin(User user,HttpSession session){
		User userOld=userService.vertifyUser(user);
		if(userOld!=null){
			Role role=userOld.getRole();
			session.setAttribute("userId",userOld.getUserId() );
			session.setAttribute("roleId",role.getRoleId() );
			switch (role.getRoleId()) {
			case 1: 
				return "/back/user/userIndex";
			case 2:
				return "/back/auditor/auditorIndex";
			case 3:
				return "forward:/back/admin";
			}
		}
			return "/back/login";
		
	}
	@RequestMapping(value="/back/loginOut",method={RequestMethod.POST})
	public String loginOut(int userId,HttpSession session){
		session.removeAttribute("userId");
		return "/back/login";
	}
}

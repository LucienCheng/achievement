package com.control;
import java.io.IOException;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.User;
import com.entity.UserCondition;
import com.service.UserService;
//管理员控制区域
@Controller
public class BackAdminControl {
	@Resource(name="UserServiceImpl")
	private UserService userService;
	private final int count=10;
	//在执行action时都会加载这个属性，当model存在的时候，就直接使用，当不存在的时候，就会进行创建，然后放到model里。相当于一个静态类。

	//首页
		@RequestMapping(value="/back/admin",method={RequestMethod.GET})
		public String index(UserCondition userCondition,Model model) {
			List<User> users=userService.getUserByConditon(userCondition, 0, count);
			model.addAttribute("users", users);
			model.addAttribute("totalCount",userService.getUserCount(userCondition));
			return "/back/amin/adminIndex";
		}
		//根据条件搜索用户
		@RequestMapping(value="/back/admin/{start}",method={RequestMethod.GET})
		@ResponseBody
		public Map<String, Object> searchUsers(@PathVariable int start,UserCondition userCondition) {
			List<User> users=userService.getUserByConditon(userCondition, start, count);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("users",users);
			map.put("totalCount",userService.getUserCount(userCondition));
			return map;
		}
	//个人资料
		@RequestMapping(value="/back/admin/personInfo",method={RequestMethod.GET})
		public String personInfo(HttpSession session,Model model) {
			model.addAttribute("user", userService.getUserById((int)session.getAttribute("userId")));
			return "/back/personInfo";
		}
		//更新用户个人的信息
		@RequestMapping(value="/back/admin/savePerson",method={RequestMethod.POST})
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
	@ModelAttribute
	 private  HSSFWorkbook resultSetToExcel(String sheetName) throws Exception  
	    {  
	        HSSFWorkbook workbook = new HSSFWorkbook();  
	        HSSFSheet sheet = workbook.createSheet("导入模板");  
	        HSSFRow row= sheet.createRow((short)0);  
	        HSSFCell cell = null; 
	        //写入各个字段的名称  
	        cell=row.createCell(0);
	        cell.setCellValue("姓名");
	        cell=row.createCell(1);
	        cell.setCellValue("性别");
	        cell=row.createCell(2);
	        cell.setCellValue("角色");
	        cell=row.createCell(3);
	        cell.setCellValue("职称");
	        cell=row.createCell(4);
	        cell.setCellValue("电话号码");
	        cell=row.createCell(5);
	        cell.setCellValue("工号");
	        return workbook;  
	    }  
	//excel导入,导入的过程有点长，所以使用异步快比较一些
	@RequestMapping(value="/back/admin/importExcel" ,method=RequestMethod.POST)
	@ResponseBody
	public  Callable<String> importExcel( final MultipartFile file){
		
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				boolean result=userService.importUsersByExcel(file);
				if (result) {
					return "success";
				}
				else {
					return "failure";
				}
				 
			}
		};
	}
	//excel模板导出
	@RequestMapping(value="/back/admin/exportModel" ,method=RequestMethod.GET)
	public  void exportModel(HSSFWorkbook workbook,HttpServletRequest request,HttpServletResponse response) throws IOException{
		  	String filename = "导入模板.xls";//设置下载时客户端Excel的名称       
	        response.setContentType("application/vnd.ms-excel");       
	        response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"ISO8859-1"));       
	        OutputStream ouputStream = response.getOutputStream();       
	        workbook.write(ouputStream);       
	        ouputStream.flush();       
	        ouputStream.close();   
	}
	
	//保存一个用户
	@RequestMapping(value="/back/admin/saveUser",method={RequestMethod.POST})
	@ResponseBody
	public String saveUser(User user){
		int result=userService.updateUser(user);
		if (result==1) {
			return "success";
		}
		else {
			return "failure";
		}
	}
	//批量删除用户
	@RequestMapping(value="/back/admin/deleteUser",method={RequestMethod.POST})
	@ResponseBody
	public String deleteUser(@RequestBody List<Integer> userIds){
		int result=userService.deleteUsers(userIds);
		if (result>0) {
			return "success";
		}
		else {
			return "failure";
		}
	}
	
	

}

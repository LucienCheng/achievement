package com.control;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.User;
import com.entity.UserCondition;
import com.mysql.fabric.xmlrpc.base.Array;
import com.service.SlideShowService;
import com.service.UserService;
//管理员控制区域
@Controller
public class BackAdminControl {
	@Resource(name="UserServiceImpl")
	private UserService userService;
	@Resource(name="slideShowImpl")
	private SlideShowService slideShowService;
	private final int count=10;	
	//在执行action时都会加载这个属性，当model存在的时候，就直接使用，当不存在的时候，就会进行创建，然后放到model里。相当于一个静态类。

	//首页
		@RequestMapping(value="/back/admin",method={RequestMethod.GET,RequestMethod.POST})
		@Transactional
		public String index(Model model,UserCondition userCondition,HttpSession session) {
			List<User> users=userService.getUserByConditon(userCondition, (Integer)session.getAttribute("userId"),0, count);
			int totalCount=userService.getUserCount(userCondition);
			if (totalCount>0) {
				totalCount--;
			}
			int totalPage=(totalCount  +  count  - 1) / count; 
			JSONArray userJson=new JSONArray(users);
			model.addAttribute("userJson",userJson);
			model.addAttribute("users",users);
			model.addAttribute("curPage", 1);
			model.addAttribute("totalCount",totalCount);
			model.addAttribute("totalPage",totalPage);
			model.addAttribute("userCondition", userCondition);
			return "/back/admin/adminIndex";
		}
		//根据条件搜索用户
		@RequestMapping(value="/back/admin/{start}",method={RequestMethod.GET,RequestMethod.POST})
		@Transactional
		public String searchUsers(@PathVariable int start,UserCondition userCondition,Model model,HttpSession session) {
			List<User> users=userService.getUserByConditon(userCondition, (Integer)session.getAttribute("userId"),(start-1)*count, count);
			int totalCount=userService.getUserCount(userCondition);
			if (totalCount>0) {
				totalCount--;
			}
			int totalPage=(totalCount  +  count  - 1) / count; 
			JSONArray userJson=new JSONArray(users);
			model.addAttribute("userJson",userJson);
			model.addAttribute("users",users);
			model.addAttribute("curPage", start);
			model.addAttribute("totalCount",totalCount);
			model.addAttribute("totalPage",totalPage);
			model.addAttribute("userCondition", userCondition);
			return "/back/admin/adminIndex";
		}
	//个人资料
		@RequestMapping(value="/back/admin/personInfo",method={RequestMethod.GET,RequestMethod.POST})
		@Transactional
		public String personInfo(HttpSession session,Model model) {
			model.addAttribute("user", userService.getUserById((int)session.getAttribute("userId")));
			return "/back/personInfo";
		}
		//更新用户个人的信息
		@RequestMapping(value="/back/admin/savePerson",method={RequestMethod.POST})
		public String updateUser(User user,HttpSession session){
			user.setUserId((int)session.getAttribute("userId"));
			int result=userService.updateUser(user);
			return "redirect:/back/admin/personInfo";
		}
	@ModelAttribute
	 private  HSSFWorkbook resultSetToExcel(String sheetName) throws Exception  
	    {  
	        HSSFWorkbook workbook = new HSSFWorkbook();  
	        HSSFSheet sheet = workbook.createSheet("导入模板");  
	        String[] sex = { "男","女" };    
	        String[] role = { "成果上传者","成果审核员","系统管理员" }; 
	        String[] position = { "教授","副教授","助理教授","讲师" }; 
	        sheet = setHSSFValidation(sheet, sex, 0, 500, 1, 1);// 第一列的前501行都设置为选择列表形式. 
	        sheet = setHSSFValidation(sheet, role, 0, 500, 2, 2);// 第一列的前501行都设置为选择列表形式. 
	        sheet = setHSSFValidation(sheet, position, 0, 500, 3, 3);// 第一列的前501行都设置为选择列表形式. 
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
	
	
	
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,    
            String[] textlist, int firstRow, int endRow, int firstCol,    
            int endCol) {    
        // 加载下拉列表内容    
        DVConstraint constraint = DVConstraint    
                .createExplicitListConstraint(textlist);    
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列    
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,    
                endRow, firstCol, endCol);    
        // 数据有效性对象    
        HSSFDataValidation data_validation_list = new HSSFDataValidation(    
                regions, constraint);    
        sheet.addValidationData(data_validation_list);    
        return sheet;    
    }    
    
    /**  
     * 设置单元格上提示  
     *   
     * @param sheet  
     *            要设置的sheet.  
     * @param promptTitle  
     *            标题  
     * @param promptContent  
     *            内容  
     * @param firstRow  
     *            开始行  
     * @param endRow  
     *            结束行  
     * @param firstCol  
     *            开始列  
     * @param endCol  
     *            结束列  
     * @return 设置好的sheet.  
     */    
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,    
            String promptContent, int firstRow, int endRow, int firstCol,    
            int endCol) {    
        // 构造constraint对象    
        DVConstraint constraint = DVConstraint    
                .createCustomFormulaConstraint("BB1");    
        // 四个参数分别是：起始行、终止行、起始列、终止列    
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,    
                endRow, firstCol, endCol);    
        // 数据有效性对象    
        HSSFDataValidation data_validation_view = new HSSFDataValidation(    
                regions, constraint);    
        data_validation_view.createPromptBox(promptTitle, promptContent);    
        sheet.addValidationData(data_validation_view);    
        return sheet;    
    }    
    
	//excel导入,导入的过程有点长，所以使用异步快比较一些
	@RequestMapping(value="/back/admin/importExcel" ,method=RequestMethod.POST)
	@Transactional
	public  Callable<String> importExcel( final MultipartFile file){
		
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				boolean result=userService.importUsersByExcel(file);
				if (result) {
					return "redirect:/back/admin";
				}
				else {
					return "redirect:/back/admin";
				}
				 
			}
		};
	}
	//excel模板导出
	@RequestMapping(value="/back/admin/exportModel" ,method={RequestMethod.GET,RequestMethod.POST})
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
	@Transactional
	public String saveUser(User user){
		System.out.println(user);
		int result=userService.updateUser(user);
		return "redirect:/back/admin";
	}
	
	//添加一个用户
		@RequestMapping(value="/back/admin/addUser",method={RequestMethod.POST})
		@Transactional
		public String addUser(User user){
			List<User> users=new ArrayList<User>();
			users.add(user);
			int result=userService.insertUsers(users);
			return "redirect:/back/admin";
		}
	//批量删除用户
	@RequestMapping(value="/back/admin/deleteUser/",method={RequestMethod.POST})
	@Transactional
	@ResponseBody
	public Map<String, Object> deleteUser(@RequestBody List<Integer> userIds){
		Map<String, Object> map=new HashMap<String, Object>();
		int result=userService.deleteUsers(userIds);
		System.out.println(userIds);
		map.put("success", "success");
		return map;
	}
	//接下是轮播图
	//轮播图页面
	@RequestMapping(value="/back/admin/slideShow",method={RequestMethod.GET,RequestMethod.POST})
	
	@Transactional
	public String slideShow(Model model){
		//选取幻灯片
		 List<Achievement> achievements=slideShowService.selectSlideShow();
		model.addAttribute("achievements",achievements);
		return "/back/admin/slideShow";
	}
	//在searchSlideShow
	@RequestMapping(value="/back/admin/slideShow/search/{start}",method={RequestMethod.GET})
	@Transactional
	public  String slideShowPage(Model model,@PathVariable("start") Integer start,@ModelAttribute AchievementCondition condition){
		if(condition!=null&&condition.getAchStartTime()!=null&&condition.getAchEndTime()!=null){
			if (condition.getAchStartTime().length()==0) {
				condition.setAchStartTime(null);
			}
			if (condition.getAchEndTime().length()==0) {
				condition.setAchEndTime(null);
			}
		}
		int totalCount=slideShowService.count(condition);
		int totalPage=(totalCount  +  count  - 1) / count; 
		model.addAttribute("achievements", slideShowService.forSlideShow(condition, (start-1)*count, count));
		model.addAttribute("curPage", start);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("condition", condition);
	return "/back/admin/searchSlideShow";
	}
	
	//轮播图的添加动作，并且会刷新slideShow
	@RequestMapping(value="/back/admin/slideShow/add",method={RequestMethod.GET,RequestMethod.POST})
	@Transactional
	public String addSlideShow( Integer achId){
		slideShowService.insertSlideShow(achId);
		
		return "forward:/back/admin/slideShow";
	}
	
	//轮播图的删除动作，并且会刷新slideShow
		@RequestMapping(value="/back/admin/slideShow/delete",method={RequestMethod.POST})
		@Transactional
		@ResponseBody
		public Map<String, Object> deleteSlideShow(@RequestBody List<Integer> achIds){
			Map<String, Object> map=new HashMap<String, Object>();
			slideShowService.deleteSlideShow(achIds);
			map.put("success", "success");
			return map;
		}

		
		
}

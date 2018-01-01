package com.control;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.service.UserService;
@Controller
public class BackUserControl {
	@Resource
	private UserService userService;
	//返回view的前缀
	private final static String prefixName="jsp/back/admin/";
	private static String getViewName(String viewName) {
		return prefixName+viewName;
	}
	//在执行action时都会加载这个属性，当model存在的时候，就直接使用，当不存在的时候，就会进行创建，然后放到model里。相当于一个静态类。
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
	@RequestMapping(value="/admin/importExcel" ,method=RequestMethod.POST)
	public  Callable<String> importExcel( final MultipartFile file){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				userService.importUsersByExcel(file);
				 return getViewName("success");
			}
		};
	}
	
	//excel模板导出
	@RequestMapping(value="/admin/exportModel" ,method=RequestMethod.GET)
	public  void exportModel(HSSFWorkbook workbook,HttpServletRequest request,HttpServletResponse response) throws IOException{
		  	String filename = "导入模板.xls";//设置下载时客户端Excel的名称       
	        response.setContentType("application/vnd.ms-excel");       
	        response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"ISO8859-1"));       
	        OutputStream ouputStream = response.getOutputStream();       
	        workbook.write(ouputStream);       
	        ouputStream.flush();       
	        ouputStream.close();   
	}
	
	

}

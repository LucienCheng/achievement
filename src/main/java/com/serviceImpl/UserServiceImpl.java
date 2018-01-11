package com.serviceImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.UserMapper;
import com.entity.Role;
import com.entity.User;
import com.entity.UserCondition;
import com.service.UserService;
@Service("userServiceImpl")
/*这是一个用户的业务逻辑的实现类*/
public class UserServiceImpl  implements UserService{
	@Resource
	private UserMapper userMapper;

	@Override
	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		UserCondition condition=new UserCondition();
		condition.setUserId(userId);
		List<User> users= getUser(condition, null, null);
		if(users.size()==0){
			return null;
		}else {
			return users.get(0);
		}
	}

	@Override
	public List<User> getUserByConditon(UserCondition condition,
			Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getUser(condition, start, count);
	}

	@Override
	public int deleteUsers(List<Integer> users) {
		// TODO Auto-generated method stub
		return userMapper.deleteUsers(users);
	}

	@Override
	public int insertUsers(List<User> users) {
		// TODO Auto-generated method stub
		return userMapper.insertUsers(users);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Override
	public List<User> getUser(UserCondition condition, Integer start, Integer count) {
		// TODO Auto-generated method stub
		return userMapper.searchUser(condition, start, count);
	}

	@Override
	public boolean importUsersByExcel(MultipartFile fileExcel) {
		// TODO Auto-generated method stub
		List<User> users=new ArrayList<User>();
		Map<String, Integer> role=new HashMap<String, Integer>();
		Map<String, Integer> sex=new HashMap<String, Integer>();
		Workbook workbook=null;
		role.put("成果上传者", 1);
		role.put("成果审核员", 2);
		role.put("系统管理员", 3);
		sex.put("男", 1);
		sex.put("女", 0);
		String fileName=fileExcel.getOriginalFilename();
		
		try(InputStream fileInputStream= fileExcel.getInputStream()){
			if(fileName.endsWith(".xls")){
				workbook=new HSSFWorkbook(fileInputStream);
			}
			else {
				workbook=new XSSFWorkbook(fileInputStream);
			}
			
			Sheet sheet=workbook.getSheetAt(0);
			int firstRow=sheet.getFirstRowNum();
			int lastRow=sheet.getLastRowNum();
			
			for(int i=firstRow+1;i<lastRow+1;i++){
				Row row=sheet.getRow(i);
				int firstColumn=row.getFirstCellNum();
				int lastColumn=row.getLastCellNum();
				User user=new User();
				Map<String, String> single=new HashMap<String, String>();
				for(int j=firstColumn;j<lastColumn;j++){
					 
					String key=sheet.getRow(0).getCell(j).getStringCellValue();
					Cell cell=row.getCell(j);
					if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
                        cell.setCellType(Cell.CELL_TYPE_STRING);  
                    } 
					String value=cell.getStringCellValue();
					single.put(key, value);
				}
			user.setRole(new Role(role.get(single.get("角色")), null));
			user.setUserName(single.get("姓名"));
			//密码默认为工号
			user.setUserPassword(single.get("工号"));
			user.setUserPhone(single.get("电话号码"));
			user.setUserPos(single.get("职称"));
			user.setUserSex(sex.get(single.get("性别")));
			user.setUserWorkNum(single.get("工号"));
			users.add(user);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		insertUsers(users);
		return true;
	}

	@Override
	public int getUserCount(UserCondition condition) {
		// TODO Auto-generated method stub
		return userMapper.searchUserCount(condition);
	}

	@Override
	public User vertifyUser(User user) {
		return userMapper.vertifyUser(user);
	}

	
	
	
}

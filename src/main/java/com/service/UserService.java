package com.service;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.entity.User;
import com.entity.UserCondition;
public interface UserService {
	//通过搜索用户id获得用户
	public User vertifyUser(User user );
	//通过搜索用户id获得用户
	public User getUserById(Integer userId );
	//通过搜索条件搜索用户
	public List<User> getUserByConditon(UserCondition condition, Integer userId,Integer start, Integer count);
	//批量删除用户
	public int deleteUsers(List<Integer> users);
	//批量插入记录
	public int insertUsers(List<User> users);
	//修改用户
	public int updateUser(User user) ;
	//execl导入用户
	public boolean importUsersByExcel(MultipartFile fileExcel) ;
	//获得总的个数
	public int getUserCount(UserCondition condition);

}

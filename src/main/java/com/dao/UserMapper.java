package com.dao;

import java.util.List;



import org.apache.ibatis.annotations.Param;

import com.entity.User;
public interface UserMapper {
	//返回用户的个数
	public int searchUserCount();
	//搜索用户
	public List<User> searchUser(@Param("userId") Integer userId,@Param("userName")String userName,
			@Param("userWorkNum")String userWorkNum,@Param("start") Integer start,
			@Param("count") Integer count);
	//批量插入记录
	public int insertUsers(List<User> users);
	//批量删除用户
	public int deleteUsers(List<Integer> users);
	//修改用户
	public int updateUser(User user) ;
	
}

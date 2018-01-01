package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Achievement;

public interface AchiMapper {
	
	public int insertAchi(Achievement achievement);//插入一条数据
	public int updateAchi(Achievement achievement);//更新一个成果
	public int deleteAchis(List<Integer> achievements);//批量删除成果
	/*
	 * @parameter:用户id，成果名，成果分类，成果的状态，数据库第几条开始，取出多少条记录,排序的条件，0代表的是最新，1代表最热
	 * @return:返回成果的列表
	 */
	public List<Achievement> searchAchi(@Param("achId") Integer achId, @Param("userId")Integer userId,
			@Param("achName")String achName,@Param("achClassify")String achClassify,
			@Param("achStatus")Integer achStatus
			,@Param("start")int start,@Param("count")int count,@Param("condition") Integer condition);//总的搜索
	
}

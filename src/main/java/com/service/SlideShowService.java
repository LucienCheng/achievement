package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Achievement;
import com.entity.AchievementCondition;

public interface SlideShowService {
	public int insertSlideShow(@Param("achId")int achId);
	public int deleteSlideShow(List<Integer> achIds);
	public List<Achievement> selectSlideShow();
	//提供给slideShow使用的搜索
	List<Achievement> forSlideShow(AchievementCondition condition,Integer start,Integer count);
}

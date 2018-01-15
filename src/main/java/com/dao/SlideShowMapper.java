package com.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Achievement;
import com.entity.AchievementCondition;
public interface SlideShowMapper {
	public int insertSlideShow(int achId);
	public int deleteSlideShow(List<Integer> achIds);
	public List<Achievement> selectSlideShow();
	public List<Achievement> forSlideShow(@Param("condition")AchievementCondition condition,
		@Param("start")Integer start,@Param("count")Integer count);
	public int count(@Param("condition")AchievementCondition condition);
	
}

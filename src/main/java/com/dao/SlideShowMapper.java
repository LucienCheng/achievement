package com.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Achievement;
import com.entity.AchievementCondition;
public interface SlideShowMapper {
	public int insertSlideShow(int achId);
	public int deleteSlideShow(int achId);
	public List<Achievement> selectSlideShow();
	public List<Achievement> forSlideShow(@Param("condition")AchievementCondition condition,
			@Param("excludeIds")List<Integer> excludeIds,@Param("start")Integer start,@Param("count")Integer count);
}

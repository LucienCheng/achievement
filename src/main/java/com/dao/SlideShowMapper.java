package com.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
public interface SlideShowMapper {
	public int insertSlideShow(int achId);
	public int deleteSlideShow(int achId);
	public List<Integer> selectSlideShow();
	public int updateSlideShow(@Param("OldAchId")int OldAchId,@Param("NewAchId")int NewAchId);
}

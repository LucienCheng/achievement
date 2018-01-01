package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SlideShowService {
	public int insertSlideShow(@Param("achId")int achId);
	public int deleteSlideShow(int achId);
	public List<Integer> selectSlideShow();
}

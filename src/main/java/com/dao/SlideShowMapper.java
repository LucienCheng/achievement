package com.dao;
import java.util.List;
public interface SlideShowMapper {
	public int insertSlideShow(int achId);
	public int deleteSlideShow(int achId);
	public List<Integer> selectSlideShow();
}

package com.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.SlideShowMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.service.SlideShowService;

@Service("slideShowImpl")
public class SlideShowImpl implements SlideShowService{
	@Resource
	private SlideShowMapper slideShowMapper;

	@Override
	public int insertSlideShow(int achId) {
		// TODO Auto-generated method stub
		return slideShowMapper.insertSlideShow(achId);
	}

	@Override
	public int deleteSlideShow(List<Integer> achIds) {
		// TODO Auto-generated method stub
		return slideShowMapper.deleteSlideShow(achIds);
	}

	@Override
	public List<Achievement> selectSlideShow() {
		// TODO Auto-generated method stub
		return slideShowMapper.selectSlideShow();
	}
	@Override
	public List<Achievement> forSlideShow(AchievementCondition condition,
			Integer start,Integer count) {
		return slideShowMapper.forSlideShow(condition, start, count);
	}

	@Override
	public int count(AchievementCondition condition) {
		// TODO Auto-generated method stub
		return slideShowMapper.count(condition);
	}

}

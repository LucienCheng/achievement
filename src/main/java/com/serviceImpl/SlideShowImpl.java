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
	public int deleteSlideShow(int achId) {
		// TODO Auto-generated method stub
		return slideShowMapper.deleteSlideShow(achId);
	}

	@Override
	public List<Achievement> selectSlideShow() {
		// TODO Auto-generated method stub
		return slideShowMapper.selectSlideShow();
	}
	@Override
	public List<Achievement> forSlideShow(AchievementCondition condition,
			List<Integer> excludeIds,Integer start,Integer count) {
		return slideShowMapper.forSlideShow(condition, excludeIds, start, count);
	}

}

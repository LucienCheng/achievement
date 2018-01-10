package com.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dao.AchiMapper;
import com.dao.AlterMapper;
import com.dao.AuditMapper;
import com.dao.SlideShowMapper;
import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.service.AchievementService;
@Service("AchievementImpl")
public class AchievementImpl implements AchievementService {
	@Resource
	private AchiMapper achiMapper;
	@Resource
	private AlterMapper alterMapper;
	@Resource
	private SlideShowMapper slideShowMapper;
	private String imaginPathPrefix="/file/image/";
	private String videoPathPrefix="/file/video/";
	static String getTime(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	private String  changeFileName(MultipartFile multipartFile){
		Date date=new Date();
		String fileName=multipartFile.getOriginalFilename();
		return date.getTime()+"_"+fileName;
	}

	
	@Override
	public String saveImagine(MultipartFile multipartFile,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String imaginName=changeFileName(multipartFile);
		String contentPathString=request.getServletContext().getRealPath("/");
		try {
			multipartFile.transferTo(new File(contentPathString,imaginPathPrefix+imaginName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request.getContextPath()+imaginPathPrefix+imaginName;
	}

	@Override
	public String saveVideo(MultipartFile multipartFile,HttpServletRequest request) {
		String videoName=changeFileName(multipartFile);
		String contentPathString=request.getServletContext().getRealPath("/");
		try {
			multipartFile.transferTo(new File(contentPathString,videoPathPrefix+videoName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request.getContextPath()+videoPathPrefix+videoName;
	}

/*以下都是关于查询成果的实现*/
	

	@Override
	public Achievement getAchiByAchId(Integer achId) {
		// TODO Auto-generated method stub
		List<Achievement> achievements= achiMapper.searchAchi(achId, 
				null, null, null, null, null, 
				null, null, null, null, null, null, null,null, null,0);
		if (achievements.size()!=0) {
			return achievements.get(0);
		}
		else {
			return null;
		}
	}
	


/*以下是插入一个成果*/
	@Override
	public int insertAchi(Achievement achievement) {
		// TODO Auto-generated method stub
		
		return achiMapper.insertAchi(achievement);
	}

/*更新一个成果*/
	
	@Override
	public int updateAchiWithSta(List<Integer> achIds, Integer achStatus) {
		// TODO Auto-generated method stub
		return achiMapper.updateAchiWithSta(achIds,achStatus);
	}
	
	
	//需要修改已经发布的成果,重新插入一个新的成果，然后记录到alterMaper中。
	@Override
	 public boolean updateAchiModify(Achievement achievementNew){
		 achievementNew.setAchDate(getTime());
		 achievementNew.setIsModify(1);
		 achievementNew.setAchStatus(0);
		 achievementNew.setAchCTR(0);
		 int oldId=achievementNew.getAchId();
		insertAchi(achievementNew);
		int newId=achievementNew.getAchId();
		alterMapper.insertAchiAlter(oldId, newId);
		return true;
		 
	 }
	 
	 //通过时，需要更新修改后的成果，并且更新引用到这个achId的地方.
	 @Transactional
	 @Override
	 public boolean updateAchiPassModify(Achievement achievementNew,Integer auditorId){
		 achievementNew.setIsModify(0);
		int oldId=alterMapper.selectAlter(achievementNew.getAchId());
		List<Integer> achievements=new ArrayList<Integer>();
		alterMapper.deleteAchiAlter(achievementNew.getAchId());
		achievements.add(oldId);
		achiMapper.deleteAchis(achievements);//删除老的已经发布的，使用新修改后的。
		slideShowMapper.updateSlideShow(oldId, achievementNew.getAchId());//更新前台幻灯片
		 achievementNew.setIsModify(0);
		 achievementNew.setAchStatus(1);
		achiMapper.updateAchi(achievementNew);//更新新的状态
		 return true;
	 }

/*批量删除成果*/
	@Override
	public int deleteAchis(List<Integer> achievements) {
		// TODO Auto-generated method stub
		return achiMapper.deleteAchis(achievements);
	}
	//最新
	@Override
	public List<Achievement> getNewAchi(String authorName, String achStartTime,
			String achEndTime, Integer start, Integer count) {
		return achiMapper.searchAchi(null, null, null, null, null, null, authorName, 
				achStartTime, achEndTime, null, null, null, null,start, count, 0);
		 
	}
	//最热
	@Override
	public List<Achievement> getHotAchi(String authorName, String achStartTime,
			String achEndTime, Integer start, Integer count) {
		achiMapper.searchAchi(null, null, null, null, null, null, authorName, achStartTime, achEndTime,
				null, null, null, null,start, count, 1);
		return null;
	}
	//后台根据条件搜索
	@Override
	public List<Achievement> getAchiCondition(Integer authorId,
			Integer auditorId, Integer achStatus, String achStartTime,String achEndTime,String audStartTime,String audEndTime,
			String achName, String authorName, String auditorName,Integer start,
			Integer count) {
		return achiMapper.searchAchi(null, authorId, auditorId, achName, null, achStatus, 
				authorName, achStartTime, achEndTime, audStartTime, auditorName, audEndTime, null,start, count, 0);
	}
	public List<Achievement> getAchiLockCondition(Integer authorId,Integer auditorId,Integer achStatus,
			String achStartTime,String achEndTime,String audStartTime,String audEndTime,String auditorName,
			String achName,String authorName,Integer achLock,Integer start,Integer count){
		return achiMapper.searchAchi(null, authorId, auditorId, achName, null, achStatus, 
				authorName, achStartTime, achEndTime, audStartTime, auditorName, audEndTime, achLock,start, count, 0);
	}
	@Override
	public boolean updateAchievement(Achievement achievement) {
		// TODO Auto-generated method stub
		achiMapper.updateAchi(achievement);
		return true;
	}
	@Override
	public int updateAchiWithLock(List<Integer> achIds, Integer achLock) {
		// TODO Auto-generated method stub
		return achiMapper.updateAchiWithLock(achIds, achLock);
	}
	@Override
	public int getCount(AchievementCondition achievementCondition) {
		// TODO Auto-generated method stub
		return achiMapper.selectCount(achievementCondition);
	}
	

	



	
}

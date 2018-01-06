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
import com.dao.ModuleMapper;
import com.dao.SlideShowMapper;
import com.entity.Achievement;
import com.entity.Audit;
import com.service.AchievementService;
@Service("AchievementImpl")
public class AchievementImpl implements AchievementService {
	@Resource
	private AchiMapper achiMapper;
	@Resource
	private AlterMapper alterMapper;
	@Resource
	private AuditMapper auditMapper;
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

	private List<Achievement> getAchis(Integer achId,Integer userId, String achName,
			String achClassify, Integer achStatus, String userName,Integer start, Integer count,
			Integer condition) {
		return achiMapper.searchAchi(achId,userId, achName, achClassify, achStatus,userName, start, count, condition);
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
	public List<Achievement> getAchiCondition(Integer userId,String achName,
			String achClassify, Integer achStatus, String userName,Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getAchis(null,userId, achName, achClassify, achStatus, userName,start, count, 0);
	}


	@Override
	public List<Achievement> getNewAchi(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getAchis(null, null, null, null, 1, null,start, count, 0);
	}

	@Override
	public List<Achievement> getHotAchi(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getAchis(null,null, null, null, 1, null,start, count, 1);
	}

	@Override
	public List<Achievement> getAchiByUserId(Integer userId, Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getAchis(null,userId, null, null,  null, null,start, count, 0);
	}
	@Override
	public Achievement getAchiByAchId(Integer achId) {
		// TODO Auto-generated method stub
		List<Achievement> achievements= getAchis(achId,null, null, null, null, null,0, 1, 0);
		if (achievements.size()!=0) {
			return achievements.get(0);
		}
		else {
			return null;
		}
	}
	@Override
	public List<Achievement> getAchiByAchiStatus(Integer userId,
			Integer achStatus, Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getAchis(null, userId, null, null, achStatus, null,start, count, 0);
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
	@Override
	public boolean updateAchiModify(Achievement achievement, Integer isModify) {
		if (isModify==0) {
			return updateAchiPassModify(achievement);
		}
		else {
			return updateAchiReModify(achievement);
		}
	}
	//需要修改已经发布的成果
	 private boolean updateAchiReModify(Achievement achievementNew){
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
	 //通过时，需要更新修改后的成果，并且展示到前台
	 @Transactional
	 private boolean updateAchiPassModify(Achievement achievementNew){
		
		int oldId=alterMapper.selectAlter(achievementNew.getAchId());
		alterMapper.deleteAchiAlter(achievementNew.getAchId());
		List<Integer> achievements=new ArrayList<Integer>();
		achievements.add(oldId);
		achiMapper.deleteAchis(achievements);
		slideShowMapper.updateSlideShow(oldId, achievementNew.getAchId());
		 achievementNew.setIsModify(0);
		 achievementNew.setAchStatus(1);
		achiMapper.updateAchi(achievementNew);
		 return true;
	 }

/*批量删除成果*/
	@Override
	public int deleteAchis(List<Integer> achievements) {
		// TODO Auto-generated method stub
		return achiMapper.deleteAchis(achievements);
	}
	

	



	
}

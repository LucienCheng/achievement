package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Achievement;
import com.entity.AchievementCondition;
import com.entity.Audit;

public interface AchievementService {
/*
 * @param:MultipartFile multipartFile（文件名）
 * @return:String （为保存的路径）
 * @author:Liao Cheng
 * @time:2017-10-28
 * 
 */
public String saveImagine(MultipartFile multipartFile,HttpServletRequest request);//保存成果的图片
/*
 * @param:MultipartFile multipartFile（文件名）
 * @return:String （为保存的路径）
 * @author:Liao Cheng
 * @time:2017-10-28
 * 
 */
public String saveVideo(MultipartFile multipartFile,HttpServletRequest request);//保存成果的视频

/*
 * @parameter:用户id，成果名，成果分类，成果的状态，数据库第几条开始，取出多少条记录,排序的条件，0代表的是最新，1代表最热
 * @return:返回成果的列表
 *以下都是查询成果的接口，只不过提供不同方式的查询。
 */

//最新的成果（前台使用）
public List<Achievement> getNewAchi(String authorName,String achStartTime,String achEndTime,Integer start,Integer count);
//最热的成果（前台使用）
public List<Achievement> getHotAchi(String authorName,String achStartTime,String achEndTime,Integer start,Integer count);
//通过achId获得某个具体的成果
public Achievement getAchiByAchId(Integer achId);
//提供给后台搜索使用.
public List<Achievement> getAchiCondition(Integer authorId,Integer auditorId,Integer achStatus,
		String achStartTime,String achEndTime,String audStartTime,String audEndTime,String auditorName,
		String achName,String authorName,Integer start,Integer count);
//提供给后台audit搜索使用.
public List<Achievement> getAchiLockCondition(Integer authorId,Integer auditorId,Integer achStatus,
		String achStartTime,String achEndTime,String audStartTime,String audEndTime,String auditorName,
		String achName,String authorName,Integer achLock,Integer start,Integer count);
//插入单个成果
int insertAchi(Achievement achievement);

//批量更新成果的状态。
public int updateAchiWithSta(List<Integer> achIds,Integer achStatus) ;


//批量更新成果的锁的状态。
public int updateAchiWithLock(List<Integer> achIds,Integer achLock) ;

//修改单个成果的内容。
public boolean updateAchievement(Achievement achievement);
//删除一个成果
public int deleteAchis(List<Integer> achievements);
//根据搜索结果返回查询的结果数量。
public int getCount(AchievementCondition achievementCondition);

}

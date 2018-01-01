package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Achievement;
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
//通过用户id来搜索成果
public List<Achievement> getAchiByUserId(Integer userId,@Param("start")Integer start
		,@Param("count")Integer count) ;
//通过搜索条件来搜索成果
public List<Achievement> getAchiCondition(@Param("userId")Integer userId,@Param("achName")String achName,@Param("achClassify")String achClassify,
		@Param("achStatus")Integer achStatus
		,@Param("start")Integer start,@Param("count")Integer count);
//最新的成果
public List<Achievement> getNewAchi(@Param("start")Integer start,@Param("count")Integer count);
//最热的成果
public List<Achievement> getHotAchi(@Param("start")Integer start,@Param("count")Integer count);
//通过achId获得
public Achievement getAchiByAchId(@Param("achId") Integer achId);
//通过成果状态获得
public List<Achievement> getAchiByAchiStatus(@Param("userId")Integer userId,
		@Param("achStatus")Integer achStatus
		,@Param("start")Integer start,@Param("count")Integer count);
/*
 * 插入一个成果
 * @param:一个成果
 * @return:返回插入的记录
 */
public int insertAchi(Achievement achievement);

/*
 * 更新成果
 * @param:一个成果
 * @return:返回插入的记录
 * 
 */
public int updateAchi(Achievement achievement);
public int updateAchiWithSta(Achievement achievement,Integer achStatus) ;
/*
 * 用于用户成功发布成果之后，需要修改重新审核时使用的api，当ismodify为1时进行修改，当为0时删除之前的成果，发布新的成果
 */
public boolean updateAchiModify(Achievement achievement,Integer isModify);

/*
 * 删除成果
 * @param:一组成果id
 * @return:返回插入的记录
 */
public int deleteAchis(List<Integer> achievements);
}

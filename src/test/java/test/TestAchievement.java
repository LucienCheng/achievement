package test;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entity.Achievement;
import com.entity.User;
import com.service.AchievementService;
@ContextConfiguration(locations={"file:WebRoot/WEB-INF/springmvc-servlet.xml","classpath:spring-mybatis.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAchievement {
	@Resource(name="AchievementImpl")
	private AchievementService achievementService;
	@Test
	public void testAchiDao(){
		/*最热的结果：*/
		List<Achievement> achievements=achievementService.getHotAchi(0, 10);
		System.out.println(achievements);
		/*最新的结果：*/
		 achievements=achievementService.getNewAchi(0, 10);
		System.out.println(achievements);
		/*按照搜索条件进行搜索*/
		achievements=achievementService.getAchiByUserId(1, 0, 10);
		achievementService.getAchiByAchiStatus(1, 0, 0, 10);
		achievementService.getAchiCondition(1, "", "", 0, 0, 10);
		achievementService.getAchiByAchId(105);
		/*插入一条记录*/
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		Date date=new Date();
		String datestring=simpleDateFormat.format(date);
		User user=new User();
		user.setUserId(1);
		Achievement achievement=new Achievement(0,"测试2",user,0,"video路径",0,"imagin路劲",0,"学习",0,
				"这是一个测试",datestring);
		achievementService.insertAchi(achievement);
		/*批量删除*/
		List<Integer> achievementsi=new ArrayList<Integer>();
		achievementsi.add(118);
		achievementsi.add(119);
	achievementService.deleteAchis(achievementsi);
		/*更新操作*/
		achievement=achievementService.getAchiByAchId(105);
		achievement.setAchLock(1);
		achievementService.updateAchi(achievement);
		achievementService.updateAchiWithSta(achievement, 1);
	}
	
	//测试对发布成果进行修改的流程
	/*@Test
	public void testModify(){
		
		Achievement achievement=achievementService.getAchiByAchId(122);
		//achievementService.updateAchiModify(achievement, 1);
		//achievementService.updateAchiModify(achievement, 0);
		achievement.setIsModify(0);
		achievementService.updateAchi(achievement);
	}*/
}

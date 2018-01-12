package com.listen;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.service.AchievementService;
import com.serviceImpl.AchievementImpl;

/**
 * Application Lifecycle Listener implementation class SessionListen
 *
 */
@WebListener
public class SessionListen implements HttpSessionListener {
private AchievementService achievementService;
    /**
     * Default constructor. 
     */
    public SessionListen() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    //需要消除已经锁的成果
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    	ApplicationContext application = WebApplicationContextUtils 
    			.getWebApplicationContext(event.getSession().getServletContext()); 
    			achievementService=(AchievementService) application.getBean("AchievementImpl");
    	List<Integer> achIds=(List<Integer>) event.getSession().getAttribute("achIds");
    	if (achIds!=null) {
			if (achIds.size()>0) {
				achievementService.updateAchiWithLock(achIds, 0);
			}
		}
    }
    
    
	
   
	
}

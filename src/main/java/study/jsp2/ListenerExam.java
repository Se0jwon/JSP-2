package study.jsp2;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class ListenerExam implements ServletContextListener, ServletContextAttributeListener
        , HttpSessionListener, HttpSessionAttributeListener {
    public ListenerExam() {

    }
    @Override
    // ServletContext 시작
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext 시작됨!!");
        sce.getServletContext().log("ServletContext 시작됨!!");
        System.out.println("====== Listener 실행됨 ======");
    }
    // ServletContext 종료
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("ServletContext 종료됨!!");
        System.out.println("ServletContext 종료됨!!");
    }
    public void attributeAdded(ServletContextAttributeEvent sce) {
        sce.getServletContext().log("ServletContext 속성 추가 :" + sce.getValue());
        System.out.println("ServletContext 속성 추가 :" + sce.getValue());
    }
    public void attributeReplaced(ServletContextAttributeEvent sce) {
        sce.getServletContext().log("ServletContext 속성 변경 : " + sce.getValue());
        System.out.println("ServletContext 속성 변경 : " + sce.getValue());
    }
    public void attributeRemoved(ServletContextAttributeEvent sce) {
        sce.getServletContext().log("ServletContext 속성 제거 :" + sce.getValue());
        System.out.println("ServletContext 속성 제거 :" + sce.getValue());
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        se.getSession().getServletContext().log("Session 속성 추가 :" + se.getValue());
        System.out.println("Session 속성 추가 :" + se.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        HttpSessionAttributeListener.super.attributeRemoved(event);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        HttpSessionAttributeListener.super.attributeReplaced(event);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().getServletContext().log("Session 생성됨: " + se.getSession().getId());
        System.out.println("Session 생성됨: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
    }
}

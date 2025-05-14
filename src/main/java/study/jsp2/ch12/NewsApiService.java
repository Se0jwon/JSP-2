package study.jsp2.ch12;

import jakarta.ws.rs.*;
import study.jsp2.ch10.News;
import study.jsp2.ch10.NewsDAO;
import jakarta.ws.rs.core.MediaType;
import study.jsp2.ch10.NewsDAO;

import java.sql.SQLException;
import java.util.List;

@Path("/news")
public class NewsApiService {
    NewsDAO dao;

    public NewsApiService() {
        dao = new NewsDAO();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNews(News news) {
        try {
            dao.addNews(news);
        } catch (Exception e) {
            e.printStackTrace();
            return "News API : 뉴스 등록 실패!";
        }
        return "News API : 뉴스 등록됨!";
    }

    @DELETE
    @Path("{aid}")
    public String delNews(@PathParam("aid") int aid) {
        try {
            dao.delNews(aid);
        } catch (SQLException e) {
            e.printStackTrace();
            return "News API : 뉴스 삭제 실패!! - " + aid;
        }
        return "News API: 뉴스 삭제됨!! - " + aid;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getNewsList() {
        List<News> newsList = null;

        try {
            newsList = dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
    @GET
    @Path("{aid}")
    @Produces(MediaType.APPLICATION_JSON)
    public News getNews(@PathParam("aid") int aid) {
        News news = null;

        try {
            news = dao.getNews(aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }
}
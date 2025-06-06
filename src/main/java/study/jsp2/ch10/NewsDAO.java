package study.jsp2.ch10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String JDBC_URL = "jdbc:h2:tcp://localhost/~/testdb";

    public Connection open(){
        Connection conn=null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, "sa", "1234");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    public void addNews(News news) throws SQLException {
        Connection conn = open();

        String sql = "insert into news(title, img, date, content) values (?, ?, CURRENT_TIMESTAMP(), ?)";

        try(conn; PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, news.getTitle());
            pstmt.setString(2, news.getImg());
            pstmt.setString(3, news.getContent());
            pstmt.executeUpdate();
        }

    }

    public List<News> getAll() throws SQLException {
        Connection conn = open();
        List<News> newsList = new ArrayList<>();

        String sql = "select aid, title, date from news";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        try(conn; pstmt; rs) {
            while(rs.next()) {
                News news = new News();
                news.setAid(rs.getInt("aid"));
                news.setTitle(rs.getString("title"));
                news.setDate(rs.getString("date"));

                newsList.add(news);
            }

            return newsList;
        }
    }

    public News getNews(int aid) throws SQLException {
        Connection conn = open();
        News news = null;

        String sql = "SELECT * from news WHERE aid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, aid);
        ResultSet rs = pstmt.executeQuery();

        try (conn; pstmt; rs) {
            if (rs.next()) {
                news = new News();
                news.setAid(rs.getInt("aid"));
                news.setTitle(rs.getString("title"));
                news.setImg(rs.getString("img"));
                news.setDate(rs.getString("date"));
                news.setContent(rs.getString("content"));
            }
        }

        return news;
    }

    public void delNews(int aid) throws SQLException {
        Connection conn = open();

        String sql = "delete from news where aid=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.setInt(1, aid);
            // 삭제된 뉴스 기사가 없을 경우
            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("DB에러");
            }
        }
    }

    public void delAllNews() throws SQLException {
        Connection conn = open();

        String sql = "delete from news";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        try (conn; pstmt) {
            pstmt.executeUpdate();
        }
    }
}

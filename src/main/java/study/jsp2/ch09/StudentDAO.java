package study.jsp2.ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    Connection conn;
    PreparedStatement pstmt;

    final String JDBC_DRIVER = "org.h2.Driver";
    final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";

    public void open() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Student student) {
        open();
        String sql = "insert into student(username, univ, birth, email) values(?,?,?,?)";

        try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, student.getUsername());
             pstmt.setString(2, student.getUniv());
             pstmt.setDate(3, student.getBirth());
             pstmt.setString(4, student.getEmail());

             pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public List<Student> getAll() {
        open();
        List<Student> students = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement("select * from student");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) { // 다음 행이 있는 동안 반복
                Student s = new Student(); // 새로운 Student 객체 생성

                // ResultSet에서 각 열의 값을 가져와서 Student 객체에 설정
                s.setId(rs.getInt("id")); // "id" 열 값을 int로 가져와서 설정
                s.setUsername(rs.getString("username")); // "username" 열 값을 문자열로 설정
                s.setUniv(rs.getString("univ")); // "univ" 열 값을 문자열로 설정
                s.setBirth(rs.getDate("birth")); // "birth" 열 값을 Date로 설정
                s.setEmail(rs.getString("email")); // "email" 열 값을 문자열로 설정

                students.add(s); // 완성된 Student 객체를 리스트에 추가
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return students;
    }
}

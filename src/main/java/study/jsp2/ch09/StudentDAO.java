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
    final String JDBC_URL = "jdbc:h2:~/testdb";

    public void open() {
        try {
            //JDBC Driver 생성
            Class.forName(JDBC_DRIVER);
            //자바 커넥션 연결
            conn = DriverManager.getConnection(JDBC_URL, "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //커넥션
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


                s.setId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setUniv(rs.getString("univ"));
                s.setBirth(rs.getDate("birth"));
                s.setEmail(rs.getString("email"));

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

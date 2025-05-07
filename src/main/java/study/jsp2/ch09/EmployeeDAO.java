package study.jsp2.ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

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

    public void insert(Employee employee) {
        open();
        String sql = "insert into employee(name) values(?)";

        try {
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, employee.getName());

             pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public List<Employee> getAll() {
        open();
        List<Employee> employees = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement("select * from employee");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();


                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));

                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return employees;
    }
}

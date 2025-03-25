package study.jsp2.ch09;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;

@WebServlet("/studentControl")
public class StudentController extends HttpServlet {

    StudentDAO studentDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentDAO = new StudentDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String view = "";

        if(action == null) {
            getServletContext().getRequestDispatcher("/studentControl?action=list")
                    .forward(req, resp);
        } else {
            switch (action) {
                case "list" -> view = list(req, resp);
                case "insert" -> view = insert(req, resp);
            }

            getServletContext().getRequestDispatcher("/ch09/" + view)
                    .forward(req, resp);
        }
    }

    public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("students", studentDAO.getAll());
        return "studentInfo.jsp";
    }

    public String insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        try {
            BeanUtils.populate(student, req.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        studentDAO.insert(student);
        return list(req, resp);
    }
}

package study.jsp2.ch09;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;

@WebServlet("/employeeControl")
public class EmployeeController extends HttpServlet {

    EmployeeDAO employeeDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employeeDAO = new EmployeeDAO();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String view = "";

        if(action == null) {
            getServletContext().getRequestDispatcher("/employeeControl?action=list")
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
        req.setAttribute("employees", employeeDAO.getAll());
        return "employeeInfo.jsp";
    }

    public String insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = new Employee();

        try {
            BeanUtils.populate(employee, req.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        employeeDAO.insert(employee);
        return list(req, resp);
    }
}

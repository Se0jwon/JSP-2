package study.jsp2.ch11;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ListenerTestServlet")
public class ListenerTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ServletContext sc;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sc = getServletContext();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sc.setAttribute("name", "홍길동");
        HttpSession s = request.getSession();
        s.setAttribute("ssName", s.getId()+": 세션 속성 저장!!");
    }
}

package study.jsp2;
// 엔딩필터
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter("/news.nhn")
public class EndingFilter implements Filter {
    private static final String ID = "jwbook";
    private static final String PASSWORD = "1234";

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        boolean loggedIn = request.getSession().getAttribute("authenticated") != null;


        if ("login".equals(action) || "login.jsp".equals(request.getRequestURI())) {
            chain.doFilter(req, res); // 로그인은 허용
        } else if (!loggedIn) {
            response.sendRedirect("ch10/login.jsp");
        } else {
            chain.doFilter(req, res);
        }
    }
}

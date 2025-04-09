package study.jsp2.ch10;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("/news.nhn")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, location = "/Users/wingwogus/IdeaProjects/JSP-2/src/main/webapp/img")
public class NewsController extends HttpServlet {

    private NewsDAO newsDAO;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        newsDAO = new NewsDAO();
        servletContext = getServletContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "listNews";
        }

        Method m;
        String view;

        try {
            m = this.getClass().getMethod(action, HttpServletRequest.class);
            view = (String) m.invoke(this, req);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        if (view != null) {
            if (view.startsWith("redirect:/")) {
                String rview = view.substring("redirect:/".length());
                resp.sendRedirect(rview);
            } else {
                req.getRequestDispatcher(view).forward(req, resp);
            }
        }
    }

    public String addNews(HttpServletRequest request){
        News n = new News();
        try {
            // 이미지 파일 저장
            Part part = request.getPart("file");
            String fileName = getFilename(part);
            if (fileName != null && !fileName.isEmpty()) {
                part.write(fileName);
            }

            // 입력값을 News 객체로 매핑
            org.apache.commons.beanutils.BeanUtils.populate(n, request.getParameterMap());

            // 이미지 파일 이름을 News 객체에도 저장
            n.setImg("/img/" + fileName);

            newsDAO.addNews(n);
        } catch (Exception e) {
            e.printStackTrace();
            servletContext.log("뉴스 추가 과정에서 문제 발생!!");
            request.setAttribute("error", "뉴스가 정상적으로 등록되지 않았습니다!!");
            return listNews(request);
        }

        return "redirect:/news.nhn?action=listNews";
    }

    public String deleteNews(HttpServletRequest request) {
        int aid = Integer.parseInt(request.getParameter("aid"));
        try {
            newsDAO.delNews(aid);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            servletContext.log("뉴스 삭제 과정에서 문제 발생!!");
            request.setAttribute("error", "뉴스가 정상적으로 삭제되지 않았습니다!!");
            return listNews(request);
        }
        return "redirect:/news.nhn?action=listNews";
    }

    public String getNews(HttpServletRequest request){
        int aid = Integer.parseInt(request.getParameter("aid"));
        try {
            News n = newsDAO.getNews(aid);
            request.setAttribute("news", n);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            servletContext.log("뉴스를 가져오는 과정에서 문제 발생!!");
            request.setAttribute("error", "뉴스를 정상적으로 가져오지 못했습니다!!");
        }
        return "ch10/newsView.jsp";
    }

    public String listNews(HttpServletRequest request){
        try {
            request.setAttribute("newsList", newsDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            servletContext.log("뉴스 목록 가져오기 실패", e);
            request.setAttribute("error", "뉴스 목록을 가져오는 데 실패했습니다.");
        }
        return "ch10/newsList.jsp";
    }

    public String delAllNews(HttpServletRequest request) {
        try {
            newsDAO.delAllNews();
        } catch (Exception e) {
            e.printStackTrace();
            servletContext.log("뉴스 전체 삭제 실패", e);
            request.setAttribute("error", "뉴스 전체 삭제에 실패했습니다.");
            return listNews(request);
        }
        return "redirect:/news.nhn?action=listNews";
    }

    private String getFilename(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}

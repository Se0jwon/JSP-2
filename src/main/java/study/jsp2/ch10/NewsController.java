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
import org.apache.commons.beanutils.BeanUtils;

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
        // 1. 파라미터 값 얻어오기
        String action = req.getParameter("action");

        // 2. 파라미터가 없다면 listNews 반환
        if (action == null) {
            action = "listNews";
        }


        Method m;
        String view;

        try {
            // 3. 이 클래스의 action 파라미터와 같은 이름을 가지고 request를 매개변수로 가진 메소드를 찾음 )
            m = this.getClass().getMethod(action, HttpServletRequest.class);
            // 4. req를 넘기며 메소드 실행 후 view 값 받아옴
            view = (String) m.invoke(this, req);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        // 뷰가 있다면
        if (view != null) {
            // 5. 데이터를 변경하는 redirect 요청이면 문자열에서 redirect 제외 후 리다이렉트
            if (view.startsWith("redirect:/")) {
                resp.sendRedirect(view.substring("redirect:/".length()));
            } else {
                // 6. 단순 조회라면 req, resp 담아서 보냄
                req.getRequestDispatcher(view).forward(req, resp);
            }
        }
    }

    // 뉴스 추가
    public String addNews(HttpServletRequest request){
        // 1. 저장할 새로운 객체 생성
        News n = new News();
        try {
            // 2. 이미지 파일 저장
            Part part = request.getPart("file");
            String fileName = getFilename(part);
            if (fileName != null && !fileName.isEmpty()) {
                part.write(fileName);
            }

            // 3. 입력값을 News 객체로 매핑
            BeanUtils.populate(n, request.getParameterMap());

            // 이미지 파일 이름을 News 객체에도 저장
            n.setImg("/img/" + fileName);

            // 4. DAO의 addNews 메소드 실행하여 뉴스 젖ㅇ
            newsDAO.addNews(n);
        } catch (Exception e) {
            e.printStackTrace();
            servletContext.log("뉴스 추가 과정에서 문제 발생!!");
            request.setAttribute("error", "뉴스가 정상적으로 등록되지 않았습니다!!");
            return listNews(request);
        }

        // 5. listNews로 리다이렉트
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

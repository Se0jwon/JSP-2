package study.jsp2.ch08;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pcontrol")
public class ProductController extends HttpServlet {

    ProductService productService;

    public ProductController() {
        productService = new ProductService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String view = "";

        if (action == null) {
            getServletContext().getRequestDispatcher("/pcontrol?action=list").forward(req, resp);
        } else {
            switch (action) {
                case "list" -> view = list(req, resp);
                case "info" -> view = info(req, resp);
            }
            getServletContext().getRequestDispatcher(view).forward(req, resp);
        }
    }

    private String info(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("p", productService.findById(req.getParameter("id")));
        return "/ch08/productInfo.jsp";
    }

    private String list(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("products", productService.findAll());
        return "/ch08/productList.jsp";
    }
}

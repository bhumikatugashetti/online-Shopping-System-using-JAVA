package servlet;

import dao.ProductDAO;
import model.Product;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Product> products;

        if (keyword == null || keyword.trim().isEmpty()) {
            products = productDAO.getAllProducts();
        } else {
            products = productDAO.searchProducts(keyword);
        }

        request.setAttribute("products", products);
        request.setAttribute("keyword", keyword);
        RequestDispatcher rd = request.getRequestDispatcher("search-results.jsp");
        rd.forward(request, response);
    }
}
package servlet;

import dao.CartDAO;
import model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CartServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cartDAO.addToCart(user.getUserId(), productId, quantity);
        }
        response.sendRedirect("cart.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("remove".equals(action)) {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            cartDAO.removeFromCart(cartId);
        }
        response.sendRedirect("cart.jsp");
    }
}
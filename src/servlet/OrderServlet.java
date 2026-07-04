package servlet;

import dao.OrderDAO;
import model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String address = request.getParameter("address");
        int orderId = orderDAO.createOrder(user.getUserId(), address);

        if (orderId > 0) {
            request.setAttribute("orderId", orderId);
            RequestDispatcher rd = request.getRequestDispatcher("order-success.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("cart.jsp");
        }
    }
}
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Order, dao.OrderDAO, model.User, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders - Online Shopping System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 20px; display: flex; justify-content: space-between; }
        .header a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 900px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f5f5f5; }
        .status { padding: 5px 10px; border-radius: 4px; font-weight: bold; }
        .pending { background: #FFC107; color: #000; }
        .completed { background: #4CAF50; color: white; }
        .shipped { background: #2196F3; color: white; }
    </style>
</head>
<body>
    <% 
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    OrderDAO orderDAO = new OrderDAO();
    List<Order> orders = orderDAO.getUserOrders(user.getUserId());
    %>
    <div class="header">
        <div><h2 style="margin:0;">My Orders</h2></div>
        <div>
            <a href="products.jsp">Products</a>
            <a href="cart.jsp">Cart</a>
            <a href="orders.jsp">My Orders</a>
            <a href="logout">Logout</a>
        </div>
    </div>
    <div class="container">
        <% if (orders.isEmpty()) { %>
            <p style="text-align:center; padding:50px;">No orders found. <a href="products.jsp">Start shopping</a></p>
        <% } else { %>
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Shipping Address</th>
                </tr>
                <% for (Order order : orders) { %>
                <tr>
                    <td>#<%= order.getOrderId() %></td>
                    <td><%= order.getOrderDate() %></td>
                    <td>$<%= order.getTotalAmount() %></td>
                    <td><span class="status <%= order.getStatus().toLowerCase() %>"><%= order.getStatus() %></span></td>
                    <td><%= order.getShippingAddress() %></td>
                </tr>
                <% } %>
            </table>
        <% } %>
    </div>
</body>
</html>
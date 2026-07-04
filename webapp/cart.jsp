<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.CartItem, dao.CartDAO, model.User, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart - Online Shopping System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; }
        .header a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 900px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f5f5f5; }
        .total { font-size: 24px; font-weight: bold; text-align: right; margin: 20px 0; }
        .btn { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-secondary { background: #888; }
        .btn-danger { background: #f44336; }
        .empty { text-align: center; padding: 50px; color: #666; }
    </style>
</head>
<body>
    <% 
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    CartDAO cartDAO = new CartDAO();
    List<CartItem> items = cartDAO.getCartItems(user.getUserId());
    double total = cartDAO.getCartTotal(user.getUserId());
    %>
    <div class="header">
        <div><h2 style="margin:0;">Shopping Cart</h2></div>
        <div>
            <a href="products.jsp">Products</a>
            <a href="cart.jsp">Cart</a>
            <a href="orders.jsp">My Orders</a>
            <a href="logout">Logout</a>
        </div>
    </div>
    <div class="container">
        <% if (items.isEmpty()) { %>
            <div class="empty">
                <h3>Your cart is empty</h3>
                <a href="products.jsp" class="btn">Continue Shopping</a>
            </div>
        <% } else { %>
            <table>
                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
                <% for (CartItem item : items) { %>
                <tr>
                    <td><%= item.getProduct().getProductName() %></td>
                    <td>$<%= item.getProduct().getPrice() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>$<%= item.getSubtotal() %></td>
                    <td>
                        <a href="cart?action=remove&cartId=<%= item.getCartId() %>" class="btn btn-danger">Remove</a>
                    </td>
                </tr>
                <% } %>
            </table>
            <div class="total">Total: $<%= total %></div>
            <div style="text-align: right;">
                <a href="products.jsp" class="btn btn-secondary">Continue Shopping</a>
                <a href="checkout.jsp" class="btn">Proceed to Checkout</a>
            </div>
        <% } %>
    </div>
</body>
</html>
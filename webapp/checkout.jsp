<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.CartItem, dao.CartDAO, model.User, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Online Shopping System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 20px; }
        .container { max-width: 600px; margin: 30px auto; padding: 20px; background: white; border-radius: 8px; }
        h2 { text-align: center; color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; min-height: 100px; }
        .summary { background: #f9f9f9; padding: 15px; border-radius: 4px; margin: 20px 0; }
        .total { font-size: 20px; font-weight: bold; text-align: right; margin: 10px 0; }
        button { width: 100%; padding: 12px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background: #45a049; }
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
        <h2 style="margin:0;">Checkout</h2>
    </div>
    <div class="container">
        <h2>Order Summary</h2>
        <div class="summary">
            <% for (CartItem item : items) { %>
                <p><%= item.getProduct().getProductName() %> x <%= item.getQuantity() %> - $<%= item.getSubtotal() %></p>
            <% } %>
        </div>
        <div class="total">Total: $<%= total %></div>
        
        <form action="order" method="post">
            <div class="form-group">
                <label for="address">Shipping Address:</label>
                <textarea id="address" name="address" required placeholder="Enter your full shipping address..."></textarea>
            </div>
            <button type="submit">Place Order</button>
        </form>
    </div>
</body>
</html>
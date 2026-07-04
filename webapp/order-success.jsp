<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Success - Online Shopping System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .container { max-width: 500px; margin: 100px auto; background: white; padding: 40px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #4CAF50; }
        .order-id { font-size: 24px; font-weight: bold; color: #333; margin: 20px 0; }
        p { color: #666; }
        .btn { display: inline-block; padding: 12px 30px; background: #4CAF50; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px; }
        .btn:hover { background: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Order Placed Successfully!</h2>
        <p>Thank you for your purchase.</p>
        <div class="order-id">Order #<%= request.getAttribute("orderId") %></div>
        <p>You can view your order in the My Orders section.</p>
        <a href="orders.jsp" class="btn">View My Orders</a>
        <a href="products.jsp" class="btn" style="background:#888; margin-left:10px;">Continue Shopping</a>
    </div>
</body>
</html>
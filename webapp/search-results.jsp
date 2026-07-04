<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Product, dao.ProductDAO, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results - Online Shopping System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 20px; display: flex; justify-content: space-between; }
        .header a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 20px auto; padding: 20px; }
        h1 { color: #333; }
        .products { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; margin-top: 20px; }
        .product-card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .price { font-size: 20px; color: #4CAF50; font-weight: bold; }
        .add-btn { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; margin-top: 10px; }
        .search-bar { margin: 20px 0; }
        .search-bar input { padding: 10px; width: 300px; border: 1px solid #ddd; border-radius: 4px; }
        .search-bar button { padding: 10px 20px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .no-results { text-align: center; padding: 50px; color: #666; }
    </style>
</head>
<body>
    <% 
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String keyword = (String) request.getAttribute("keyword");
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.searchProducts(keyword != null ? keyword : "");
    %>
    <div class="header">
        <div><h2 style="margin:0;">Search Results</h2></div>
        <div>
            <a href="products.jsp">Products</a>
            <a href="cart.jsp">Cart</a>
            <a href="orders.jsp">My Orders</a>
            <a href="logout">Logout</a>
        </div>
    </div>
    <div class="container">
        <div class="search-bar">
            <form action="search" method="get">
                <input type="text" name="keyword" value="<%= keyword != null ? keyword : "" %>" placeholder="Search products...">
                <button type="submit">Search</button>
            </form>
        </div>
        <% if (products.isEmpty()) { %>
            <div class="no-results">
                <h3>No products found for "<%= keyword %>"</h3>
                <a href="products.jsp">View all products</a>
            </div>
        <% } else { %>
            <div class="products">
                <% for (Product p : products) { %>
                    <div class="product-card">
                        <h3><%= p.getProductName() %></h3>
                        <p><%= p.getDescription() %></p>
                        <p><strong>Category:</strong> <%= p.getCategory() %></p>
                        <p class="price">$<%= p.getPrice() %></p>
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                            <input type="number" name="quantity" value="1" min="1" max="<%= p.getStockQuantity() %>" style="width:60px;">
                            <button type="submit" class="add-btn">Add to Cart</button>
                        </form>
                    </div>
                <% } %>
            </div>
        <% } %>
    </div>
</body>
</html>
# 🛒 Online Shopping System

A full-featured **Online Shopping System** built with **Core Java**, featuring both a **Desktop GUI** (Java Swing) and a **Web Interface** (JSP + Servlets). The application connects to a **MySQL database** via JDBC and supports customer shopping, cart management, order tracking, and an admin panel.

---

## 📸 Features

### 👤 Customer Features
- User Registration & Login
- Browse & Search Products by category or keyword
- Add/Remove items from Shopping Cart
- Place Orders and track Order History
- View Order Success confirmation

### 🛠️ Admin Features
- Admin Login Panel (separate from customer login)
- Manage Products (add, view, update stock)
- View and manage all Customer Orders
- Dashboard with order and inventory overview

### 🖥️ Dual Interface
| Interface | Technology | How to Run |
|-----------|------------|------------|
| Desktop App | Java Swing (GUI) | `run.bat` |
| Web App | JSP + Servlets | Deploy on Apache Tomcat |

---

## 🏗️ Project Architecture

```
online Shopping System/
│
├── src/
│   ├── gui/            # Java Swing Desktop UI
│   │   ├── Main.java
│   │   ├── LoginFrame.java
│   │   ├── RegisterFrame.java
│   │   ├── CustomerFrame.java
│   │   ├── AdminFrame.java
│   │   ├── AdminLoginFrame.java
│   │   └── Calculator.java
│   │
│   ├── servlet/        # Web layer (Servlets for web app)
│   │   ├── LoginServlet.java
│   │   ├── RegisterServlet.java
│   │   ├── CartServlet.java
│   │   ├── OrderServlet.java
│   │   ├── SearchServlet.java
│   │   └── LogoutServlet.java
│   │
│   ├── dao/            # Data Access Objects (Database logic)
│   │   ├── UserDAO.java
│   │   ├── ProductDAO.java
│   │   ├── CartDAO.java
│   │   └── OrderDAO.java
│   │
│   ├── model/          # Java Model/Entity classes
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   └── Order.java
│   │
│   └── db/
│       └── DBConnection.java   # JDBC Database Connection
│
├── webapp/             # Web application (JSP pages)
│   ├── index.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── products.jsp
│   ├── cart.jsp
│   ├── checkout.jsp
│   ├── orders.jsp
│   ├── order-success.jsp
│   ├── search-results.jsp
│   └── WEB-INF/
│       └── web.xml
│
├── lib/                # External JAR dependencies
│   ├── mysql-connector-j-8.0.33.jar
│   └── javax.servlet-api-4.0.1.jar
│
├── build/              # Compiled class files
├── build.xml           # Ant build configuration
├── run.bat             # Launcher for Desktop GUI
├── setup-database.bat  # Database setup script
└── xampp-setup.sql     # SQL schema & seed data
```

---

## 🗄️ Database Schema

The system uses a **MySQL** database (`online_shopping`) with the following tables:

| Table | Description |
|-------|-------------|
| `users` | Stores customer and admin accounts |
| `products` | Product catalog with pricing, stock, and category |
| `orders` | Order header records linked to users |
| `order_items` | Line items for each order |
| `cart` | Persisted shopping cart items per user |

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java (Core Java) |
| Desktop UI | Java Swing |
| Web UI | JSP (JavaServer Pages) |
| Web Backend | Java Servlets |
| Database | MySQL (via XAMPP) |
| DB Connectivity | JDBC (`mysql-connector-j-8.0.33`) |
| Build Tool | Apache Ant (`build.xml`) |
| Web Server (optional) | Apache Tomcat |

---

## 🚀 Getting Started

### Prerequisites

- ✅ [Java JDK 8+](https://www.oracle.com/java/technologies/downloads/)
- ✅ [XAMPP](https://www.apachefriends.org/) (for MySQL database)
- ✅ [Apache Ant](https://ant.apache.org/) (for building the project)
- ✅ *(Optional)* [Apache Tomcat](https://tomcat.apache.org/) (for the web interface)

---

### 🗃️ Step 1: Set Up the Database

**Option A – Using the batch script:**
```bash
setup-database.bat
```

**Option B – Using phpMyAdmin:**
1. Open XAMPP and start **Apache** and **MySQL**
2. Go to `http://localhost/phpmyadmin`
3. Import the `xampp-setup.sql` file

This will create the `online_shopping` database with all tables and sample data including:
- 3 sample users (1 admin + 2 customers)
- 8 sample products across Electronics, Clothing, Footwear, and Home categories

---

### 🖥️ Step 2A: Run the Desktop Application

```bash
run.bat
```

This launches the **Java Swing GUI** application. You can log in as:

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `password123` |
| Customer | `john` | `password123` |
| Customer | `jane` | `password123` |

---

### 🌐 Step 2B: Run the Web Application (Optional)

1. Build the project using Ant:
   ```bash
   ant build
   ```
2. Deploy the generated WAR/files to **Apache Tomcat**
3. Access the web app at `http://localhost:8080/online-shopping/`

---

## 🧱 Key Java Concepts Used

| Concept | Usage in Project |
|---------|-----------------|
| **Java Strings** | Storing usernames, passwords, SQL queries, product names |
| **Java Swing** | Building the entire Desktop GUI (JFrame, JButton, JTable) |
| **JSP** | Creating dynamic web pages for browser-based access |
| **JDBC** | Bridging Java and MySQL via `PreparedStatement` & `ResultSet` |
| **DAO Pattern** | Separating database logic from business and UI logic |
| **MVC Pattern** | Model (entities) → Servlet (controller) → JSP (view) |
| **OOP** | Encapsulation via model classes (User, Product, Order, CartItem) |

---

## 📦 Sample Product Data

| Product | Category | Price |
|---------|----------|-------|
| Laptop | Electronics | $999.99 |
| Smartphone | Electronics | $699.99 |
| Headphones | Electronics | $199.99 |
| T-Shirt | Clothing | $29.99 |
| Jeans | Clothing | $49.99 |
| Sneakers | Footwear | $89.99 |
| Coffee Maker | Home | $79.99 |
| Desk Lamp | Home | $39.99 |

---

## 🤝 Contributing

Contributions are welcome! Feel free to:
- Fork the repository
- Create a new branch (`git checkout -b feature/your-feature`)
- Commit your changes (`git commit -m 'Add some feature'`)
- Push to the branch (`git push origin feature/your-feature`)
- Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

Developed as a Java learning project demonstrating core concepts including Swing GUI, Servlets, JSP, JDBC, and the DAO design pattern.

> ⭐ If you found this project helpful, please consider giving it a star!

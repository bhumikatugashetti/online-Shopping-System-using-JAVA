@echo off
echo ========================================
echo    Setting up Database for
echo    Online Shopping System
echo ========================================
echo.

echo Make sure XAMPP MySQL is running!
echo Then open phpMyAdmin at: http://localhost/phpmyadmin
echo.

echo Option 1: Copy and paste the following SQL commands in phpMyAdmin:
echo ---------------------------------------------------------------------------
type "C:\Users\sagar\Desktop\online Shopping System\src\db\database.sql"
echo ---------------------------------------------------------------------------
echo.

echo Option 2: Or run this command in XAMPP MySQL command line:
echo mysql -u root < "C:\Users\sagar\Desktop\online Shopping System\src\db\database.sql"
echo.

echo After setup, you can run the application with:
echo java -cp "build\classes;lib\mysql-connector-j-8.0.33.jar" gui.Main
echo.

pause
@echo off
title Online Shopping System
cd /d "%~dp0"
java -cp "build\classes;lib\mysql-connector-j-8.0.33.jar" gui.Main
pause
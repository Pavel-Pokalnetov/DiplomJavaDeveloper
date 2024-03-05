docker-compose.exe up -d
::java.exe -jar target\AppMonitoring-0.0.1-SNAPSHOT.jar
start "AppMon" appMon.exe -jar target\AppMonitoring-0.0.1-SNAPSHOT.jar
pause

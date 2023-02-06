echo start
cd /d %~dp0\pack\nart\src\main\resources\redis\
start redis-server.exe
start "C_C Front_end" cmd /k "cd /d %~dp0\C_C_front && npm run dev"
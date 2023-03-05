import psutil
import os

# Get the process ID (PID) of the running Python script

pid = None
for p in psutil.process_iter(attrs=['pid', 'name']):
    if p.info['name'] == 'python.exe' and 'your_script_name.py' in p.cmdline():
        pid = p.info['pid']
        break
while(1):
 if pid is not None:
    os.kill(pid, 9) # 9 is the signal to terminate the process
    print(f"Process with PID {pid} has been terminated.")
    
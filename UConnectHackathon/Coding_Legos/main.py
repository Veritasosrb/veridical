# Create an EC2 instance and then attach EBS volume to it(either already created and avialable or create a new one)

from create_instance import create_instance
from getInfo import getInfo

res = create_instance()

if(not res):
    print("Code exited with code 1")
else:
    print(f"\033[32mInstance {res.get('instance')} created and attached to {res.get('volume')} successfully!!\033[0m")
    getInfo(res.get('instance'))

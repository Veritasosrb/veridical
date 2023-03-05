import boto3

# importing access keys to aws account
from access_keys import AWS_ACCESS_KEY_ID, AWS_REGION, AWS_SECRET_ACCESS_KEY

from create_EBSvolume import createEBSVolume

# create an instance with mysql server installed and configured
def create_instance():
    try:
        # 
        print("\033[33mStarting the process of creating EC2 instance\033[0m")

        # Set up the EC2 client
        ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                           aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)
        
        # create EBS volume and return volume Id
        volume = createEBSVolume()

        if(not volume):
            print("\033[33mPlease try again executing script\033[0m")
            return None

        print("\033[32mVolume created successfully\033[0m")

        # Initializing configuration variables---------------------------------------------------------------------------------
        print("\033[33mInitializing configuration variables\033[0m")

        # Define the parameters for the new EC2 instance
        ami_id = 'ami-0f8ca728008ff5af4'  # ID of the Amazon Linux 2 AMI
        instance_type = 't2.micro'  # Instance type (e.g. t2.micro)
        key_name = 'coding_legos'  # Name of the SSH key pair
        # ID of the security group
        security_group_ids = ['sg-09528519175248189']
        subnet_id = 'subnet-01de472ab9f2a8d7c'  # ID of the subnet
        user_data = '''#!/bin/bash
        apt-get update -y
        apt-get install -y mysql-server
        systemctl start mysql
        mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '_mvfN+C@Ft4wPnME'; FLUSH PRIVILEGES;"
        systemctl status mysql.service

        snap refresh amazon-ssm-agent
        list amazon-ssm-agent
        snap start amazon-ssm-agent
        snap services amazon-ssm-agent
        '''

        # User data script

        # Creating instance------------------------------------------------------------------------------------------------
        print("\033[33mCreating instance\033[0m")

        instances = ec2.run_instances(
            ImageId=ami_id,
            InstanceType=instance_type,
            KeyName=key_name,
            SecurityGroupIds=security_group_ids,
            SubnetId=subnet_id,
            UserData=user_data,
            MinCount=1,
            MaxCount=1
        )

        # Waiting for instance to be start----------------------------------------------------------------------------------
        print("\033[33mWaiting for instance to be start\033[0m")

        # Wait for instance to start
        instance_id = instances['Instances'][0]['InstanceId']

        # in-case waiter didn't work
        # time.sleep(60)

        # create waiter object for instance to wait until it is running
        waiter = ec2.get_waiter('instance_status_ok')
        waiter.wait(InstanceIds=[instance_id])

        print(f'\033[32mInstance with ID - {instance_id} is created successfully\033[0m')

        volume_id = volume['VolumeId']
        volume_response = ec2.attach_volume(
            VolumeId=volume_id, InstanceId=instance_id, Device='/dev/xvdf')
        print("\033[32mVolume attached successfully\033[0m")

        # wait for the volume to be attached
        waiter = ec2.get_waiter('volume_in_use')
        waiter.wait(
            VolumeIds=[
                volume_id,
            ],
            Filters=[
                {
                    'Name': 'attachment.status',
                    'Values': [
                        'attached',
                    ],
                },
            ],
        )

        return {'instance': instance_id, 'volume': volume_id}
    except Exception as err:
        print("\033[31mException occured during instance creation\033[0m")
        print(f"\033[31mException: {err}\033[31m")
        return None
import boto3

# AWS credentials and region
AWS_ACCESS_KEY_ID = 'AKIAUWEEJNNP7Q6E6U45'
AWS_SECRET_ACCESS_KEY = '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'
AWS_REGION = 'ap-south-1'


def attachEBSVolume():
    try:
        # Set up the EC2 client
        ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                           aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)

        ans = input("Create EC2 instance with new EBS volume attached(y/n): ")
        if(ans == "y" or ans == "Y" or ans == "Yes" or ans == "yes"):
            # create an EBS volume
            volume = ec2.create_volume(
                AvailabilityZone=AWS_REGION+"a",
                Encrypted=True,
                Size=5,
                VolumeType='gp2'
            )

            # wait for the volume to be available
            waiter = ec2.get_waiter('volume_available')
            waiter.wait(VolumeIds=[volume['VolumeId']])

            return volume
        else:
            # Call the describe_volumes method to get a list of all volumes
            response = ec2.describe_volumes()

            in_useCount = 0

            # Iterate through the list of volumes and print the details
            for volume in response['Volumes']:
                if(volume["State"] == "available"):
                    print(
                        f"Volume ID: \033[32m{volume['VolumeId']}\033[0m, Size: {volume['Size']} GB, Availability Zone: {volume['AvailabilityZone']}, State: \033[32m{volume['State']}\033[0m")
                else:
                    in_useCount += 1
                    print(
                        f"Volume ID: \033[31m{volume['VolumeId']}\033[0m, Size: {volume['Size']} GB, Availability Zone: {volume['AvailabilityZone']}, State: \033[31m{volume['State']}\033[0m")

            # If all volumes are in use then exit
            if in_useCount == len(response['Volumes']):
                print(
                    "\033[31mError: All volumes present are already in use\033[0m")
                return None

            while(True):
                ans = input(
                    "\nEnter Volume ID to attach to instance(should be available): ")

                for volume in response['Volumes']:
                    if(ans == volume['VolumeId'] and volume['State'] == 'available'):
                        return volume
                print("\033[31mError: Invalid volume Id..\033[0m")
                print(
                    "\033[33mWarning: Either volume Id doesn't exist or it is not in available state\033[0m")
    except Exception as err:
        print(f"Unexpected {err=}, {type(err)=}")
        raise


def create_instance():
    try:
        # init--------------------------------------------------------------------------------------------------------
        print("Starting the process of creating EC2 instance..")

        # Set up the EC2 client
        ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                           aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)

        volume = attachEBSVolume()

        if(not volume):
            print("Please try again executing script")
            return

        print("Volume created successfully..")

        # Initializing configuration variables---------------------------------------------------------------------------------
        print("Initializing configuration variables..")

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
        '''

        # User data script

        # Creating instance------------------------------------------------------------------------------------------------
        print("Creating instance..")

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
        print("Waiting for instance to be start..")

        # Wait for instance to start
        instance_id = instances['Instances'][0]['InstanceId']

        # in-case waiter didn't work
        # time.sleep(60)

        # create waiter object for instance to wait until it is running
        waiter = ec2.get_waiter('instance_running')
        waiter.wait(InstanceIds=[instance_id])

        print(f'Instance with ID - {instance_id} is created successfully..')

        volume_id = volume['VolumeId']
        volume_response = ec2.attach_volume(
            VolumeId=volume_id, InstanceId=instance_id, Device='/dev/xvdf')
        print("Volume attached successfully..")
        print(volume_response)

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

        print(instances['Instances'][0])
        instance_response = ec2.describe_instances(InstanceIds=[instance_id])
        public_ip_address = instance_response['Reservations'][0]['Instances'][0]['PublicIpAddress']

        print("Formatting new volume..")
        ssm = boto3.client('ssm')
        response = ssm.send_command(
            InstanceIds=[instance_id],
            DocumentName="AWS-RunShellScript",
            Parameters={'commands': [
                'echo "Connecting to EC2 instance..."',
                f'ssh -i /{key_name}.pem coding_legos@{public_ip_address}',
                'sudo /mkfs -t ext4 /dev/xvdf',
                'sudo mount /dev/xvdf /mnt/backup_volume',
                'sudo mkdir /sql_backup',
                'sudo mount /dev/xvdf /sql_backup',
                'echo "Disconnecting from EC2 instance.."']
            }
        )

        # Get the command ID and output
        command_id = response['Command']['CommandId']
        output = ''
        while True:
            status = ssm.get_command_invocation(
                CommandId=command_id, InstanceId=instance_id)
            if status['Status'] in ['Pending', 'InProgress']:
                continue
            elif status['Status'] == 'Success':
                output = status['StandardOutputContent']
            else:
                output = status['StandardErrorContent']
            break

        # Print the output
        print(output)

        # SSH into instance and restore SQL backup file
        # print('Restoring MySQL database from backup file...')
        # ssh_cmd = f'ssh -i {key_name}.pem ec2-user@{instances["Instances"][0]["PublicIpAddress"]} "mysqlbackup --backup-image={backup_file} --backup-dir=/tmp --uncompress --force && mysql -u root < /tmp/backup.sql"'
        # subprocess.run(ssh_cmd, shell=True)
        # print('MySQL database restored successfully.')
    except Exception as err:
        print(f"Unexpected {err=}, {type(err)=}")
        raise


def terminate_instance(instance_id):
    # Set up the EC2 client
    ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                       aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)
    # Terminate instance
    response = ec2.terminate_instances(
        InstanceIds=[instance_id],
    )
    print("EBS volume attached successfully")


# print(f"Volume Id: {attachEBSVolume()}")
create_instance()
# terminate_instance('i-0afe6733b7453140b')

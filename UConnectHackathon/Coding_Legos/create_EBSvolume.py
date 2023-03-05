import boto3

# importing access keys to aws account
from access_keys import AWS_ACCESS_KEY_ID, AWS_REGION, AWS_SECRET_ACCESS_KEY

def createEBSVolume():
    try:
        # Set up the EC2 client
        ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                           aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)

        ans = input("\033[33mCreate EC2 instance with new EBS volume attached(y/n): \033[0m")
        if(ans == "y" or ans == "Y" or ans == "Yes" or ans == "yes"):
            # create an EBS volume with size 5 GiB
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
                    "\n\033[33mEnter Volume ID to attach to instance(should be available)\033[0m: ")

                for volume in response['Volumes']:
                    if(ans == volume['VolumeId'] and volume['State'] == 'available'):
                        return volume
                print("\033[31mError: Invalid volume Id\033[0m")
                print(
                    "\033[33mWarning: Either volume Id doesn't exist or it is not in available state\033[0m")
    except Exception as err:
        print("\033[31mException occured during EBS volume creation\033[0m")
        print(f"\033[31mException: {err}\033[31m")
        return None
import boto3

# importing access keys to aws account
from access_keys import AWS_ACCESS_KEY_ID, AWS_REGION, AWS_SECRET_ACCESS_KEY


def getInfo(instance_id):
    ec2 = boto3.client('ec2', aws_access_key_id=AWS_ACCESS_KEY_ID,
                       aws_secret_access_key=AWS_SECRET_ACCESS_KEY, region_name=AWS_REGION)
    instance_response = ec2.describe_instances(InstanceIds=[instance_id])
    public_dns_address = instance_response['Reservations'][0]['Instances'][0]['PublicIpAddress']
    print(f"\033[32mInstance public dns: {public_dns_address}\033[0m")
    
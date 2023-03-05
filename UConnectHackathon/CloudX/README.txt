Steps to check miscofiguration using WebApp: 

1. Fork and Clone repository
2. install the packages using command ~  npm install
3. Start the server using command ~ node index.js
4. Open localhost:8000 in any browser
5. Enter a bucket name you want to check misconfiguration for and Click submit.
6. If the bucket is publically accessible it's content will be displayed else an error will be thrown.


Steps to check S3 bucket misconfigurations using AWS CLI: 

1. Connect linux EC2 instance or open linux command line prompt.
2. Clone repository there.
3. Change directory to ShellScript.
4. Run command ~ pip install -r requirements.txt
5. check if bucket is public run command ~ aws s3 ls s3://BUCKETNAME --no-sign-request  
6. if prompted (access denied) Run command ~ aws configure
7. Enter security credentials 
8. -u for single bucket
   run command ./bucketveritas.sh -u BUCKETNAME  
    


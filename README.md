## Description
The special Application for changing null values in aws s3 to the proper value
  
The Java application takes the following parameters:  
- [x] AWS Key  
- [x] AWS Secret  
- [x] S3 bucket name  
- [x] S3 bucket prefix  
- [x] First Date  
- [x] Last Date  

The application shall scan/index all files under  
``` 
s3://bucketname/someprefix/*  
```
If their modified date is between first to the last date, and 
the file path/name conforms with this pattern (null refers to a string with a value of "null"):  
s3://bucketname/prefix/<some string>/null/<blank or some string>_null.txt, 
then rename the file to  
s3://bucketname/prefix/<some string>/313/<blank or some string>_313.txt  

### Type of change:
- Development from the scratch

### Details
Was developed with Spring boot and Maven for Java 11

### How Has This Been Tested?

Tested in my self free AWS account
Written tests for checking invocation of methods

### How to use?

 - Copy jar file to your AWS server  
``
scp -i "user.pem" BDT-16-91.jar ec2-user@ec2-XX-XX-XXX-XXX.eu-north-1.compute.amazonaws.com:home/ec2-user
``
 - Go to your server  
``
sh -i "user.pem" ec2-user@ec2-XX-XX-XXX-XXX.eu-north-1.compute.amazonaws.com
``
 - Start application for change files and folders names on s3 server  
``
java -jar BDT-1691-1.jar 
--cloud.aws.credentials.access-key=XXXXX (required)  
--cloud.aws.credentials.secret-key=XXXXX (required)
--app.bucket-name= bucketname   
--app.bucket-prefix= prefix (required)  
--app.first-date=XX-XX-XXXX (required) 
--app.last-date=XX-XX-XXXX  
``  
- Values that you can set as execution argument
``
    --cloud.aws.credentials.access-key=XXXXX (required) 
    --cloud.aws.credentials.secret-key=XXXXX (required) 
    --app.bucket-name=XXXXX (required)
    --app.bucket-prefix=XXXXX 
    --app.first-date=XX-XX-XXXX (required)
    --app.last-date=XX-XX-XXXX (required)
`` 
- In addition we can define  
``
    --cloud.aws.region.auto=false (true by default) 
    --cloud.aws.region.static=XXXXX
    --app.replace-value=XXXXX 
    --app.replace-to=XXXXX (313 as default)
``
- You can change values for replacing
``
    --app.replace-value=XXXXX (null by default)
    --app.replace-to=XXXXX (313 as default)
``
### Note
There is possible to appear an exception  
``
SdkClientException: The requested metadata is not found at http://XXX.XXX.XXX.XXX/latest/user-data
``
Execution of application does not depend on this exception
It depends on settings on your server

If you want to execute application on your local machine, you need to set your proper region
By default --cloud.aws.region.auto=true  
``
    --cloud.aws.region.auto=false (true by default)
    --cloud.aws.region.static=XXXXX
``
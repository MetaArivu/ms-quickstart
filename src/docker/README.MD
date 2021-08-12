# Create Docker Container

## Step 1 - Setting Microservice Name

1. Change the micro service name in the file SERVICE_NAME
2. Change the Org namme in the file ORG_NAME
3. Change the EntryPoint in the Dockerfile (Last line - jar file name) 

## Step 2 - Build the image

1. build.sh
2. securityScan

## Step 3 - Test the image

1. start.sh
2. docker container ls (it shld display the sevice - mentioned in the SERVICE_NAME) as the output - running since.... 
3. Check the URL in a Browser
4. docker logs <container-id> (To Troubleshoot)

## Step 4 - Push the image to Cloud Repo

1. docker push org/image

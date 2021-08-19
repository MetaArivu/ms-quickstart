# Create Docker Container
# -------------------------------------------------------------------------------------------

## Step 1 - Setting Microservice Name

1. Update the microoservice name in src/main/resources/app.props.tmpl file (service.api.name)
2. Update the Org Name in src/main/resources/app.props.tmpl file (service.org)
3. Change the EntryPoint in the Dockerfile (Last line - jar file name) 

## Step 2 - Build the image

1. build
2. scan

## Step 3 - Test the image

1. start
2. logs (to view the container logs) - Wait for the Container to Startup
3. Check the URL in a Browser

## Step 4 - Push the image to Cloud Repo

1. push 

## Other Commands

1. stop (Stop the Container)_
2. stats (show container stats)

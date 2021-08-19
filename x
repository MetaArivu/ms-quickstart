#!/bin/sh
serviceName=1
orgName=2
param=$1
echo Input = $param
if [ $param == $serviceName ]
then
	echo "Condition  1"
	exit
elif [ $param == $orgName ] 
then
	echo "Condition  2"
	exit
fi
echo "Condition  3"

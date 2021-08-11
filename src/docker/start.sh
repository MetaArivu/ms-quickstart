#!/bin/bash
SERVICE=mock-payment
echo "docker container run --name $SERVICE -d --rm -p 9010:9090 metamagic/$SERVICE"
docker container run --name $SERVICE -d --rm -p 9010:9090 metamagic/$SERVICE

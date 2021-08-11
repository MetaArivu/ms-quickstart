#!/bin/bash
SERVICE=mock-payment
echo "docker container stop $SERVICE"
docker container stop $SERVICE

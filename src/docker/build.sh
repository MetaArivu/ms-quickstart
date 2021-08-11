#!/bin/bash
SERVICE=mock-payment
echo "docker build --force-rm -f Dockerfile -t metamagic/$SERVICE . "
docker build --force-rm -f Dockerfile -t metamagic/$SERVICE . 

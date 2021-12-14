#!/bin/sh
docker rm -f $(docker ps -a -q -f "name=tfg-monitor-system")
docker volume rm  tfg-monitor-system_mongo-vol
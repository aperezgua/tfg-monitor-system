#!/bin/sh
docker rm -f $(docker ps -a -q -f "name=tfg-monitor-system")
docker volume rm  tfg-monitor-system_mongo-vol
docker-compose -p tfg-monitor-system -f tfg-monitor-system.yml up -d
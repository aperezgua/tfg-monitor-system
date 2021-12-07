#!/bin/sh
docker rm -f $(docker ps -a -q)
docker-compose -f tfg-monitor-system.yml up -d
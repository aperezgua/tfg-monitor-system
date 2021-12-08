#!/bin/sh
docker rm log4j-appender
docker run --restart always --name log4j-appender -d log4j-appender:1.0-SNAPSHOT
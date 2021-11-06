# tfg-monitor-system
Proyecto de monitorización de sistemas – Abel Pérez Guardado

## Descripción de los módulos

## Docker
Fichero `mongo.yml` y `mongo.init.js` que son usados como configuración para arrancar el Docker de MongoDB en desarrollo.

### Módulo commmon
Sistema de información del software en donde se definen los Document que son la representación de los datos en Java.

## Módulo user-microservice:
Microservicio

## Preparación de entorno de desarrollo
Para el entorno de desarrollo es necesario disponer de una base de datos MongoDB, para ello se usará Docker, Docker Hub y
Docker Compose para descargar y arrancar dicha imagen. 

1. Descargar imagen de mongo Docker Hub con el siguiente comando: `docker pull mongo` 
2. Arrancar imagen con Docker Compose: `docker-compose -f mongo.yml up`
3. Acceder al módulo de administración: [http://127.0.0.1:8081](http://127.0.0.1:8081)

# tfg-monitor-system
Proyecto de monitorización de sistemas – Abel Pérez Guardado

## Descripción de los módulos

## Docker
Carpeta con lo necesario para levantar un sistema de dockers de desarrollo.

## Módulo commmon
Sistema de información del software en donde se definen los Document y los sistemas base de atutenticación para que los
microservicios pueden usarlos.

## Módulo auth-microservice:
Microservicio de autenticación.
Recibe petición POST a [http://127.0.0.1:8091/authenticate](http://127.0.0.1:8091/authenticate) con json:

```json
{
    "username":"aperezgua@uoc.edu",
    "password":"pw"
}
```

## Módulo user-microservice:
Microservicio de consulta de usaurios:

Recibe petición GET a  [http://localhost:8092/rest/users/all](http://localhost:8092/rest/users/all) con cabecera Authorization con el token generado por auth-microservice.

## Preparación de entorno de desarrollo
Para el entorno de desarrollo es necesario disponer de una base de datos MongoDB, para ello se usará Docker, Docker Hub y
Docker Compose para descargar y arrancar dicha imagen. 

1. Descargar imagen de mongo Docker Hub con el siguiente comando: `docker pull mongo` 
2. Arrancar imagen con Docker Compose: `docker-compose -f mongo.yml up`
3. Acceder al módulo de administración: [http://127.0.0.1:8081](http://127.0.0.1:8081)
4. Arrancar auth-microservice
5. Arrancar user-microservice
6. Arrancar webapp

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

o

```json
{
    "username":"sam@uoc.edu",
    "password":"pw"
}
```

```json
{
    "agentTokenId":"0bac5204-4951-11ec-81d3-0242ac130003"
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
6. Arrancar system-microservice
7. Arrancar agent-microservice
8. Arrancar log-microservice
9. Arrancar log4j-appender (opcional)
10. Arrancar webapp

## Despliegue en entorno de preproducción:
Para el despliegue de entorno de preproducción se debe realizar una compilación con el perfil docker: `mvn clean install docker:build` 
lo que generará las imágenes de Docker necesarias para ejecutar el `docker-compose -f tfg-monitor-system.yml up -d`


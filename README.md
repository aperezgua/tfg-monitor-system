# tfg-monitor-system
Proyecto de monitorización de sistemas – Abel Pérez Guardado

## Descripción de los módulos

## Docker
Carpeta con lo necesario para levantar un sistema de dockers de desarrollo:
* mongo.yml: fichero de docker-compose para arrancar una mongodb compatible con la configuración del proyecto con perfil
dev.
* tfg-monitor-system.yml: fichero de docker-compose para arrancar el conjunto de microservicios del aplicativo que están
desplegados.
* mongo-init.js: fichero de configuración de credenciales de mongo.
* start.sh: comando sh que elimina los contenedores docker del sistema de tfg-monitor-system y los vuelve a arrancar 
reinicializando la base de datos.
* stop.sh: comando sh ue elimina los contenedores docker del sistema de tfg-monitor-system.
* start-mongo.bat: comando bat para arrancar mongo en windows para desarrollo.

## Módulo commmon
Sistema de información del software en donde se definen los Document y los sistemas base de atutenticación para que los
microservicios pueden usarlos.

## Módulo auth-microservice:
Microservicio de autenticación

** /authenticate **
* Tipo de petición: POST
* Ejemplo de post:

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

El servicio devolverá una respuesta con un token que debe ser usado en cada una de las peticiones que se vayan a realizar
a los microservicios .

### Ejemplo de cabecera de autenticación:

```
Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcGVyZXpndWFAdW9jLmVkdSIsIm5hbWUiOiJBYmVsIiwiZXhwIjoxNjM5NDQyNDQzLCJpYXQiOjE2Mzk0MjQ0NDMsInJvbCI6IkFETUlOSVNUUkFUT1IifQ.mYF-FiUyhyMbnFV6eDdDeoMOX0lb2fpzya1fayyIuq0keXGYsRfMHZlW4-yRYEiv7AsGyCtSbeaYBjLjDVS5gg
```

## Módulo user-microservice:
Microservicio de consulta de usuarios:

** /rest/users/find **
* Tipo de petición: POST
* Ejemplo de petición:

```json
{
    "name" : "",
    "email" : "",
    "activeTypeFilter" : ALL
}
```

** /rest/users/get/{id} **
* Tipo de petición: GET
* Ejemplo de petición: /rest/users/get/10001

** /rest/users/put **
* Tipo de petición: POST
* Ejemplo de petición:

```json
{  
    name : '',
    email : '',
    password : '',
    active : true,
    rol : 'ADMINISTRATOR'
}
```

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
se usa un sistema de parametrización substituyendo las variables @server.host@ y @mongodb.host@


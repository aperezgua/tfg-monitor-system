# tfg-monitor-system
Proyecto de monitorización de sistemas – Abel Pérez Guardado

## Descripción de los módulos

**Docker**
Carpeta con lo necesario para levantar un sistema de dockers de desarrollo o preproducción:
* mongo.yml: fichero de docker-compose para arrancar una mongodb compatible con la configuración del proyecto con perfil
dev.
* tfg-monitor-system.yml: fichero de docker-compose para arrancar el conjunto de microservicios del aplicativo que están
desplegados.
* mongo-init.js: fichero de configuración de credenciales de mongo.
* start.sh: comando sh que elimina los contenedores docker del sistema de tfg-monitor-system y los vuelve a arrancar 
reinicializando la base de datos.
* stop.sh: comando sh ue elimina los contenedores docker del sistema de tfg-monitor-system.
* start-mongo.bat: comando bat para arrancar mongo en windows para desarrollo.

***

**Módulo commmon:**
Sistema de información del software en donde se definen los Document y los sistemas base de atutenticación para que los
microservicios pueden usarlos.

***

**Módulo auth-microservice:** 
Microservicio de autenticación.

***

**Módulo user-microservice:**
Microservicio con apirest para la gestión de usuarios.

***

**Módulo system-microservice:**
Microservicio con apirest para la gestión de sistemas.

***

**Módulo agent-microservice:**
Microservicio con apirest para la gestión de agentes.

***

**Módulo log-microservice:**
Microservicio con apirest para la gestión de log.

***

**Módulo webapp:**
Servidor web de aplicativo react.

***

**Módulo log4j-appender:**
Appender de log4j con aplicativo de ejemplo generador de log aleatorio.


## Preparación de entorno de desarrollo

Para el entorno de desarrollo es necesario disponer de una base de datos MongoDB, para ello se usará Docker-desktop para
arrancar la imagen de mongo usando docker-compose. Esta configuración funciona con localhost por

- Arrancar de mongo usando el fichero: docker/start-mongo.bat
- Acceder al módulo de administración de mongo: [http://127.0.0.1:8081](http://127.0.0.1:8081)
- Arrancar auth-microservice
- Arrancar user-microservice
- Arrancar system-microservice
- Arrancar agent-microservice
- Arrancar log-microservice
- Arrancar log4j-appender (opcional generador de log)
- Arrancar webapp ([ver webapp/README para configuración](./webapp/README.md) )

## Perfiles de maven:
* dev: Perfil por defecto que define la base de datos y todos los servicios contra localhost.
* docker-local: Perfil para compilar y generar imágenes de docker en arranque local, asegurándose que se especifica adecuadamente
el host de mongo de la red interna y el la IP sobre la cual docker está realizando NAT.
* docker: Perfil para generar imágenes para un servidor linux público.


## Despliegue en entorno de docker:
Para el despliegue de entorno de preproducción en una máquina pública se debe realizar una compilación con el perfil docker 
que se desee (docker o docker-local):  `mvn clean install docker:build -P docker`  

Este comando se encargará de realizar la compilación, pruebas de test, checkstyle, spotbugs y si todo resulta satisfactorio,
generará las imágenes de Docker.

Finalmente, con el comando `docker-compose -f tfg-monitor-system.yml up -d` se podrá levantar un sistema de contenedores
que pongan en funcionamiento el aplicativo. 


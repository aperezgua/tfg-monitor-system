# Docker
Fichero `mongo.yml` y `mongo.init.js` que son usados como configuración para arrancar el Docker de MongoDB en desarrollo.

1. Descargar imagen de mongo Docker Hub con el siguiente comando: `docker pull mongo` 
2. Arrancar imagen con Docker Compose: `docker-compose -f mongo.yml up` o usando el script `start-mongo.bat` 
3. Acceder al módulo de administración: [http://127.0.0.1:8081](http://127.0.0.1:8081)

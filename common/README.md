# common
Módulo que contiene el sistema de información del proyecto, en el son declarados los diferentes Documents que serán usados
por el aplicativo para acceder a MongoDb. También se incluye el sistema de autenticación JWT para que los microservicios
lo usen como base para realizar la autenticación de las peticiones.

## Package controller.security
Se declaran los filtros, utilidades y demás clases necesarias para interceptar cada petición y verificar que los token son válidos.

## Package model.document
Se declaran los documentos que van a ser usados por el sistema.

## Package model.repository
Se declaran los repositorios de MongoDb que será usados para gestionar los documentos y colecciones.

## Package view
Se declaran las vistas comunes a todos los microservicios.



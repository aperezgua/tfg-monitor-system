# user-microservice
Microservicio de gestión de usuarios 

Puerto: 8092

## Api REST:

**/rest/users/find**

Busca usuarios según un filtro.
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
    "name" : "",
    "email" : "",
    "activeTypeFilter" : ALL
}
```

***

**/rest/users/get/{id}**

Obtiene un usuario según su id.
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

***

**/rest/users/put**

Guarda un usuario en la base de datos.
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
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


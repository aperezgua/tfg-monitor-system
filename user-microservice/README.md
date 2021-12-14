# user-microservice
Microservicio de gestión de usuarios 

Puerto: 8092

** /rest/users/find **
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

** /rest/users/get/{id} **
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR


** /rest/users/put **
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


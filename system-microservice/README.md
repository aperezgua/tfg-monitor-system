# system-microservice
Microservicio de gestión de sistemas

Puerto 8093

## Api REST:

**/rest/systems/find**

Busca sistemas según un filtro.
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
    "name" : "",
    "countryId" : "",
    "activeTypeFilter" : ALL
}
```

***

**/rest/systems/get/{id}**

Obtiene un sistema en concreto según su id.
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

***

**/rest/systems/put**

Guarda un sistema en la base de datos.

* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{  
    name : '',
    country : {},
    active : true
}
```

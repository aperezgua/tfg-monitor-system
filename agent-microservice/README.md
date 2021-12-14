# system-microservice
Microservicio de gestión de agentes

Puerto 8094

## Api Rest:

**/rest/agents/find**
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
    "name" : "",
    "systemId" : "",
    "activeTypeFilter" : ALL
}
```

**/rest/agents/findLastNotificationData**
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

**/rest/agents/generateToken**
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

**/rest/agents/get/{token}**
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

**/rest/agents/put**
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
     token : '',
     name : '',
     systems : {},
     active : true,
     rules : []
}
```

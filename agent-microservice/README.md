# system-microservice
Microservicio de gestión de agentes

Puerto 8094

## Api Rest:

**/rest/agents/find**

Busca documentos de tipo Agent en el sistema y los devuelve.
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

***

**/rest/agents/findLastNotificationData**

Obtiene los últimos agentes notificados.
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

***

**/rest/agents/generateToken**

Genera un token para un agente de manera aleatoria.
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

***

**/rest/agents/get/{token}**

Obtiene los datos de un documento Agente en concreto según su id.
* Tipo de petición: GET
* Roles permitidos: ADMINISTRATOR

***

**/rest/agents/put**

Guarda los datos del agente.
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

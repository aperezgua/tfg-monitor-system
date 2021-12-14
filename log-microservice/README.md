# log-microservice
Microservicio de gestión de log de los agentes 

Puerto 8095

## Api Rest:

**/rest/eventLog/findEventSummary **
* Tipo de petición: POST
* Roles permitidos: SUPPORT
* Ejemplo de petición:

```json
{
    agentTokenId : "",
    limitResults : "",
    systemIds : [],
    lastTimeInSeconds: "",
    severity : "",
    ruleName : ""
}
```
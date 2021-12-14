# log-microservice
Microservicio de gestión de log de los agentes 

Puerto 8095

## Api Rest:

**/rest/eventLog/findEventSummary **

Devuelve un resumen de eventos calculando sus diferentes incrementos respecto un tiempo  especificado por lastTimeInSeconds
y un tipo de severidad..

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

**/rest/eventLog/findLastLogEvents**

Devuelve un listado de documentos LogEvent que pueden ser procesados por la vista.

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

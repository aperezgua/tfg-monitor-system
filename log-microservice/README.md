# log-microservice
Microservicio de gestión de log de los agentes 

Puerto 8095

## Api Rest:

**/rest/eventLog/findEventSummary **

Devuelve un resumen de eventos calculando sus diferentes incrementos respecto un tiempo  especificado por lastTimeInSeconds
y un tipo de severidad.
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

***

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

***

**/rest/log/findByRegexp**

Busca últimas líneas de log según regexp.
* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
    agentTokenId : "",
    regexp : "",
    limitResults : ""
}
```

***

**/rest/log/put**

Guarda una línea de log de agente.
* Tipo de petición: POST
* Roles permitidos: AGENT
* Ejemplo de petición:

```
Linea de log
```

***

**/rest/updateAgentEvents**

Actualiza los eventos de log de un agente determinado porque se han actualizado sus reglas.

* Tipo de petición: POST
* Roles permitidos: ADMINISTRATOR
* Ejemplo de petición:

```json
{
    agentTokenId : "",
    regexp : "",
    limitResults : ""
}
```
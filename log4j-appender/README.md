# log-appender
Sistema de appender. 

## Configuración:
Se debe agregar la librería de appender al classpath del aplicativo y a continuación modificar el log4j.xml para agregar
un  nuevo appender con el token del agente, el microservicio de autenticación y el microservicio
de guardar log:

```
 <appender name="APPENDER_NAME" class="edu.uoc.tfgmonitorsystem.log4j.appender.AgentAppender">        
    <param name="authenticationUrl" value="AUTHENTICATION_URL" />
    <param name="putLogUrl" value="SAVE_URL" />
    <param name="agentToken" value="TOKEN_ID" />
    <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
    </layout>
</appender>
```

## Aplicativo de prueba:
Este módulo dispone de un aplicativo de prueba que genera log aleatorio para dos agentes configurados:

- Agente 1: ac714c1a-4953-11ec-81d3-0242ac130003  "Se ha producido un error en el sistema", "Nivel crítico" y "NullPointerException"
- Agente 2: 0bac5204-4951-11ec-81d3-0242ac130003  "El sistema no responde", "Password incorrecta" y  "NoSuchElementException"

Para ejecutar esta prueba se debe ejecutar la clase main `edu.uoc.tfgmonitorsystem.log4j.app.Log4jTestApp`
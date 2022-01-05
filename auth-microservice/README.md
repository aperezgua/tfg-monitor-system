# auth-microservice

Microservicio de autenticación de usuarios y agentes. Se encarga de recibir peticiones de agentes y usuarios y generar un
token válido.

Puerto: 8091

## Api Rest:

**/authenticate**
* Tipo de petición: POST
* Ejemplo de post:

```json
{
    "username":"aperezgua@uoc.edu",
    "password":"pw"
}
```

o

```json
{
    "username":"sam@uoc.edu",
    "password":"pw"
}
```

```json
{
    "agentTokenId":"0bac5204-4951-11ec-81d3-0242ac130003"
}
```

El servicio devolverá una respuesta con un token que debe ser usado en cada una de las peticiones que se vayan a realizar
a los microservicios .

### Ejemplo de cabecera de autenticación:

```
Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcGVyZXpndWFAdW9jLmVkdSIsIm5hbWUiOiJBYmVsIiwiZXhwIjoxNjM5NDQyNDQzLCJpYXQiOjE2Mzk0MjQ0NDMsInJvbCI6IkFETUlOSVNUUkFUT1IifQ.mYF-FiUyhyMbnFV6eDdDeoMOX0lb2fpzya1fayyIuq0keXGYsRfMHZlW4-yRYEiv7AsGyCtSbeaYBjLjDVS5gg
```

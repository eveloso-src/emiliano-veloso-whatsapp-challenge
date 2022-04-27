## Environment set up

- Run the following command from terminal: \ 
  
  docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management

  It will download a docker image and run that into a container with a rabbit mq server running
  
- Start MySQL Server in port 3306 --> Once the project starts, it will create the database under whatsapp schema using root as user and password
\
- Using a http client (i.e. Postman) invoke a post request to the URL http://localhost:8080/wa/v1/send/
with the following body as an example: 
{ "message": "texto1",
 "source" : "1234",
 "destination"  : "group1"
}

- When the endpoint is invoked, the request will be validated (no empty fields allowed), sent to the destination queue. In this scenario,
the group "group1" is created for listening to messages. The request if valid is also saved into the database. Otherwise it will return 
a 400 HTTP status code.


- Swagger: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

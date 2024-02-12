Tiene Redis para la Cache. Solo se cachean las findById y se borra cuando se actualiza. Se ha puesto un log en el service para ver cuando se esta usando.

Para levantar el docker de Redis:

  En la ruta del proyecto ejecutar -> docker-compose up -d


Documentación con OpenAPI:

  Para ver la interface grafica de swagger -> http://localhost:8080/swagger-ui/index.html
  
  Para importar las pruebas de manera automatica en Postman -> http://localhost:8080/v3/api-docs

  Ejemplo de body para las pruebas:    
                                    {    
                                    "name": "Superman",
                                    
                                    "birthDate": "1986-02-28",
                                    
                                    "vulnerability": "Kriptonita"
                                    
                                    }  

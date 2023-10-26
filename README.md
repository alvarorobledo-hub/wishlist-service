# Getting Started

### Prerequisitos

#### Necesario

* JDK 11 Disponible [aquí](https://adoptopenjdk.net/)
* Maven Disponible [aquí](https://maven.apache.org/)

#### Opcional

* Docker desktop Disponible [aquí](https://www.docker.com/products/docker-desktop)

### Guía
#### Contenido

* Spring-boot app
	- Expone un servicio REST de ejemplo 
* WireMock mock server
	- Expone la API definida en src/main/resources/productsApi.yml
* Sonar instance (para poder levantar sonar es necesario docker)
	- localhost:9000
	- admin/admin

#### Uso

* Arranque de la aplicación: mvn spring-boot:run
* Análisis de código (necesario docker): 
   1. Levantar instancia de sonar: mvn docker-compose:up
   2. Análisis de código: mvn clean verify sonar:sonar
   
### Objetivo

Se desea implentar un servicio que permita a los usuarios guardar productos en una lista para comprar más tarde. Esta lista se quiere identificar mediante un nombre único que no podrá ser repetido, además se quiere limitar el número de listas por usuarios a 5. Estas listas no podrán superar más de 25 artículos. A la hora de crear una lista es necesario que se le pase el nombre y que contenga al menos un producto. Si una lista se queda sin productos hay que eliminarla.
Actualmente, ya existe una api que nos devuelve el detalle de los articulos, a continuación se detallan las operaciones y datos que nos puede devolver esta API:

* Se expone en localhost:8081
* La definición de la api viene dada por el fichero productsApi.yml que contiene el proyecto
* Devuele el detalle para un total de 15 productos identificados por un id comprendido entre 1 y 15
* Si se le pasa un identificador mayor a 16 devolverá un 404

Al existir un servicio que ya contiene el detalle de productos nuestro servicio únicamente deberá de guardar los identificadores de articulos y a la hora de devolver el detalle de una lista deberá de hacer uso de la API de productos. En caso de no encontrar un producto en la API no se deberá de devolver en la respuesta de la lista y habrá que eliminarlo de esta. 

Las operaciones que se quieren implementar son las siguientes:

* Crear una lista para un usuario
* Añadir un producto a una lista 
* Eliminar una lista de un usuario
* Eliminar un producto de la lista
* Devolver el detalle de una lista por id
* Devolver todas las listas de un usuario



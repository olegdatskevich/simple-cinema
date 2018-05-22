[![Build Status](https://travis-ci.org/olegdatskevich/simple-cinema.svg?branch=master)](https://travis-ci.org/olegdatskevich/simple-cinema)
[![Coverage Status](https://coveralls.io/repos/github/olegdatskevich/simple-cinema/badge.svg?branch=master)](https://coveralls.io/github/olegdatskevich/simple-cinema?branch=master)

## Description    
Web application for cinema. The application is designed to work with movies and seances.  
The project consists of two cooperating applications, designed to work with information about movies and seances.
  
**cinemarest.war** — REST service providing access to a database of movies and seances.

**cinemaweb.war** — Web application providing an interface for working with the REST service.
## Used technologies
* JDK 1.8  
* Spring 4 Framework  
* Maven v3.2.2
* Jetty v.9.4.8  
* H2 database v1.4.196

## Build project 
       $mvn clean install  

## Run REST-service  
       $mvn -pl rest/ jetty:run  
#### Create movie  
       $ curl -H "Content-Type: application/json" -X POST -d '{"movieName":"Movie 42","movieDescription":"Ha-Ha"}' -v localhost:8088/movies
#### Read movie       
       $ curl -v localhost:8088/movies
       $ curl -v localhost:8088/movies/1
#### Update movie  
       $ curl -X PUT -H "Content-Type: application/json" -d '{"movieId":"1","movieName":"New Movie","movieDescription":"Ho-Ho"}' -v localhost:8088/movies
#### Delete movie  
       $ curl -X PUT -H "Content-Type: application/json" -d '{}' -v localhost:8088/movies/1
       
## Run WEB-application  
       $mvn -pl web-app/ jetty:run  
##### The application should be available at:       
       http://localhost:8080/movies  
##  
by **Oleg Datskevich**  
e-mail: olegdac@gmail.com  
tel: +375(29)5266584
# Exchange office
System implemented in Microservice Architecture

## Main repository 
-> This repository gathers all microservices and projects involved in the development of a exchange-office system.

## Repositories used for development of the microservices
-> <a href="https://github.com/pedroluiznogueira/microservices-currency-conversion-service">Currency Conversion Microservice</a> <br>
-> <a href="https://github.com/pedroluiznogueira/microservices-currency-exchange-service">Currency Exchange Microservice</a> <br>
-> <a href="https://github.com/pedroluiznogueira/microservice-spring-cloud-config-server">Limits Microservice</a> <br>
<br>
-> <a href="https://github.com/pedroluiznogueira/kubernetes">Docker Techniques Used Documented</a> <br>
-> <a href="https://github.com/pedroluiznogueira/docker">Kubernetes and Google Cloud Kubernetes Engine Techniques Used Documented</a> <br>
-> <a href="https://github.com/pedroluiznogueira/microservices-api-gateway">API Gateway</a> <br>
<br>
-> <a href="https://github.com/pedroluiznogueira/microservice-spring-cloud-config-server">Spring Cloud Centralized Configuration</a> <br>
<br>
-> <a href="https://github.com/pedroluiznogueira/microservice-spring-cloud-config-server">Centralized Configuration With Git Repository</a> <br>
<br>
-> <a href="https://github.com/pedroluiznogueira/microservice-spring-cloud-config-server">Spring Cloud Centralized Configuration</a> <br>
<br>
-> <a href="https://github.com/pedroluiznogueira/microservice-spring-cloud-config-server">Eureka Server Configuration</a> <br>


<hr>

### Centralized Configuration

![img_5.png](assets/img_5.png)

### Limits Service

- configuration dependency

![img_11.png](assets/img_11.png)

![img.png](assets/img.png)

- that's the reference to the centralized configuration server
- the limits will be retrieved from the config server

![img_1.png](assets/img_1.png)

- we can define them hard coded in properties
- these values

![img_3.png](assets/img_3.png)

- are mapped to those attributes

![img_2.png](assets/img_2.png)

<hr>

### Spring cloud config server

- naming the applications is a good practice

![img_4.png](assets/img_4.png)

- that application will fetch data from the files
- wich are on the git repository

![img_6.png](assets/img_6.png)

- then each microservice will fetch the centralized
- spring cloud config server wich fetches from the
- git repository

![img_7.png](assets/img_7.png)

- since the spring cloud config server runs in port
- 8888, if we define the profile as qa, the data fetched
- will be

![img_8.png](assets/img_8.png)

- so each microservice can define each own profile to
- pass forward to the spring cloud config application
- to allow the configurarion we need to enable it using

![img_9.png](assets/img_9.png)

- checking the spring cloud configuration server
- 
![img_10.png](assets/img_10.png)

<hr>

### Currency Exchange & Currency Conversion

![img_14.png](assets/img_14.png)

![img_12.png](assets/img_12.png)

![img_13.png](assets/img_13.png)

### Currency Exchange

![img_15.png](assets/img_15.png)

- good practices

![img_16.png](assets/img_16.png)

- connecting to the spring cloud config server

![img_17.png](assets/img_17.png)

- currency exchange entity

![img_18.png](assets/img_18.png)

- dynamic ports in the response

![img_19.png](assets/img_19.png)

- we need to know wich instance of the currency exchange
- is providing the response when we call it from the
- currency conversion, so we know if the load balancer
- is working fine

![img_20.png](assets/img_20.png)

![img_21.png](assets/img_21.png)

- no matter what instance is being called, the port it is
- up on, will be sent in the response
- we cant test it locally setting the server.port and run 
- different projects for the same microservice in intellij

<br>

- setting up in memory database to mock some data in /resources
- import to remember h2 and jpa dependency

![img_22.png](assets/img_22.png)

- fetching the data from database

![img_23.png](assets/img_23.png)

- payload

![img_24.png](assets/img_24.png)

### Currency Conversion

- currency conversion will fetch data from currency exchange
- so we can know the necessary data to make the conversion

![img_28.png](assets/img_28.png)

- good practice

![img_25.png](assets/img_25.png)

- currency conversion entity

![img_30.png](assets/img_30.png)

- communication between the microservice without Feign

![img_29.png](assets/img_29.png)

- communication between the microservice with Feign

- spring cloud feign dependency

![img_34.png](assets/img_34.png)

![img_31.png](assets/img_31.png)

![img_32.png](assets/img_32.png)

<hr>

### Naming server

- the problem is if we need to change the port that we call
- from the currency conversion through feign to currency
- exchange, we need to hard code it and go and change

![img_35.png](assets/img_35.png)

- is kind of like the naming server is asked for a instance
- and together with the load balancer it gives the instances
- back to who calls it

<br>

- we can use Eureka Server from spring cloud

![img_36.png](assets/img_36.png)

- enabling Eureka Server

![img_37.png](assets/img_37.png)

- good practice

![img_38.png](assets/img_38.png)

- since we don't want it self to register in the server

![img_39.png](assets/img_39.png)

- connecting the microservices to the naming server
- we need to add the eureka dependency to the microservices

![img_40.png](assets/img_40.png)

- just from adding the dependency it will connect to the naming
- server, but is better to make it explicit

![img_41.png](assets/img_41.png)

### Load balancing

- we want to load balance the intances of currency exchange
- when they are called from the currency conversion microservice
- we just need to make the feign call it like this

![img_42.png](assets/img_42.png)

- so the instances registers in eureka, locally we can see it
- when running two different projects of the same microservice
- so once we call the currency exchange microservices it is
- load balanced

<br>

- lately the load balancer was ribbon, but now it is implemented
- by eureka

<hr>

### API Gateway

- lately it was implemented with Zuul, but now it is implemented
- by spring cloud gateway

<br>

- we want the api gateway to connect to eureka server, so we need
- the eureka dependency

![img_40.png](assets/img_40.png)

- spring cloud gateway dependency

![img_43.png](assets/img_43.png)

- good practice

![img_44.png](assets/img_44.png)

- registering api gateway to the naming server

![img_45.png](assets/img_45.png)

- the idea is to the api gateway to recieve a request and then
- talk to the naming server, find the refereed microservice 
- end-point as pass it forward

<br>

- since it is already registered in eureka, we just configure

![img_47.png](assets/img_47.png)

- now every request go through the gateway

![img_48.png](assets/img_48.png)

- customizing the routes to be redirecteds

![img_49.png](assets/img_49.png)

- filtering requests from gateway

![img_50.png](assets/img_50.png)

- pointing out spring cloud gateway

![img_51.png](assets/img_51.png)

<hr>

### Circuit breaker

- lately Hystrix was the used circuit breaker
- but Resillience4j was implemented to take care of Java 8 features

![img_52.png](assets/img_52.png)

- needed dependencies

![img_53.png](assets/img_53.png)

- @Retry
- it could be a call to some other api using feign

<br>

- with default, if it fails it makes three attempts, only then if
- it continues failing it return the error back

![img_55.png](assets/img_55.png)

- custom retry configuration

![img_58.png](assets/img_58.png)

![img_56.png](assets/img_56.png)

- fallback
- it refeers to a method in the class, and it could return anything
- it could be some fallback response stored in database, memory, cache
- or some file

![img_59.png](assets/img_59.png)

- all of the retry configurations comes into play when handling with
- fallback responses

![img_60.png](assets/img_60.png)

- @CircuitBreaker
- we can test it with a combination of curl and watch

![img_61.png](assets/img_61.png)

- the circuite breaker gives the return back without calling
- the methods
- because it sees that there are so many requests
- so it break the circuit and return responses

<br>

- close state
- calling continuosly
- open state
- it won't call the directed microservice, return the fallback directly
- half open state
- sending a percentage of requests, and then returns the fallback

<br>

- rate limits for a specific api call in the method you use it

![img_62.png](assets/img_62.png)

![img_63.png](assets/img_63.png)

- bulkhead limits concurrent calls

![img_64.png](assets/img_64.png)

![img_65.png](assets/img_65.png)





























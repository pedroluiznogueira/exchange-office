# Project urls

    ### Currency Exchange Service
    - http://localhost:8000/currency-exchange/from/USD/to/INR

    ### Currency Conversion Service
    - http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
    - http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

    ### Eureka
    - http://localhost:8761/

    ### Zipkin
    - http://localhost:9411/

    ### API GATEWAY
    - http://localhost:8765/currency-exchange/from/USD/to/INR
    - http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
    - http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10
    - http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10

# Dockerizing these Projects

    ### Dependencies
    <build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<image>
						<name>pedroluiznogueira/ms-${project.artifactId}:${project.version}</name>
					</image>
					<pullPolicy>IF_NOT_PRESENT</pullPolicy>
				</configuration>
			</plugin>
		</plugins>
	</build>

    ### Compile the project on IntelliJ
    - Maven -> compile

    ### Build the project's Docker Image
    - ./mvnw spring-boot:build-image -DskipTests

    ### Open cmd and run the Docker Image as a Container
    docker run -p 8000:8000 pedroluiznogueira/ms-currency-exchange:0.0.1.SNAPSHOT  

# Run all the microservices together

    ### Go to the folder where all the microservices are
    - create a docker-compose.yaml file

    ### File content
    version: '3.7'

        services:

        currency-exchange:
            image: pedroluiznogueira/ms-currency-exchange-service:0.0.1-SNAPSHOT
            mem_limit: 700m
            ports:
            - "8000:8000"
            networks:
            - currency-network
            depends_on:
            - naming-server
            - rabbitmq
            environment:
            EUREKA.CLIENT.SERVICEURL.DEFAULTZONE: http://naming-server:8761/eureka
            SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/
            RABBIT_URI: amqp://guest:guest@rabbitmq:5672
            SPRING_RABBITMQ_HOST: rabbitmq
            SPRING_ZIPKIN_SENDER_TYPE: rabbit

        currency-conversion:
            image: pedroluiznogueira/ms-currency-conversion-service:0.0.1-SNAPSHOT
            mem_limit: 700m
            ports:
            - "8100:8100"
            networks:
            - currency-network
            depends_on:
            - naming-server
            - rabbitmq
            environment:
            EUREKA.CLIENT.SERVICEURL.DEFAULTZONE: http://naming-server:8761/eureka
            SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/
            RABBIT_URI: amqp://guest:guest@rabbitmq:5672
            SPRING_RABBITMQ_HOST: rabbitmq
            SPRING_ZIPKIN_SENDER_TYPE: rabbit

        api-gateway:
            image: pedroluiznogueira/ms-api-gateway:0.0.1-SNAPSHOT
            mem_limit: 700m
            ports:
            - "8765:8765"
            networks:
            - currency-network
            depends_on:
            - naming-server
            - rabbitmq
            environment:
            EUREKA.CLIENT.SERVICEURL.DEFAULTZONE: http://naming-server:8761/eureka
            SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/
            RABBIT_URI: amqp://guest:guest@rabbitmq:5672
            SPRING_RABBITMQ_HOST: rabbitmq
            SPRING_ZIPKIN_SENDER_TYPE: rabbit

        naming-server:
            image: pedroluiznogueira/ms-naming-server:0.0.1-SNAPSHOT
            mem_limit: 700m
            ports:
            - "8761:8761"
            networks:
            - currency-network

        #docker run -p 9411:9411 openzipkin/zipkin:2.23

        zipkin-server:
            image: openzipkin/zipkin:2.23
            mem_limit: 300m
            ports:
            - "9411:9411"
            networks:
            - currency-network
            environment:
            RABBIT_URI: amqp://guest:guest@rabbitmq:5672
            depends_on:
            - rabbitmq
            restart: always #Restart if there is a problem starting up

        rabbitmq:
            image: rabbitmq:3.8.12-management
            mem_limit: 300m
            ports:
            - "5672:5672"
            - "15672:15672"
            networks:
            - currency-network


        networks:
        currency-network:

#### MORE DETAILED GUIDE CAN BE FOUND ON THE MAIN PROJECT READ.ME
# Tutorial

# Docker image
-> We say image when refering to a application using docker but staticly

# Docker container
-> We say container when refering to a application using docker but dinamicly

# Running
docker run in28min/todo-rest-api-h2:1.0.0.RELEASE
docker run repositoryNameInHub:versionDeployed

# Running in a specif port
docker run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE -> you can run it in another port with the same image
docker run port HostPort:ContainerPort repositoryNameInHub:versionDeployed

# Detached
docker run -p 5000:5000 -d in28min/todo-rest-api-h2:1.0.0.RELEASE
-> You recieve a key and don't see the logs

# Seeing log
docker logs
docker logs key
docker logs -f key -> logs with queries

# Seeing live containers
docker container ls

# Seeing off containers
docker container ls -a

# Stopping
docker container stop key

# Docker pull repositoryName
-> brings a specific repository to your local machine

# Images history
docker image history f8049a029560

# Image inspect
docker image inspect key

# Removing image
docker image remove key

# Pausing a container
docker container pause key

# Unpausing a container
docker container unpause key

# Stop a container
docker container stop key

# Start existing container
docker container start key

# Inspect container
docker container inspect key

# Prune container
docker container prune -> delete all stopped containers

# STOP - SIGTERM 
graceful shutdown, think about 10 seconds and stop

# SIGKILL - Kill container
docker kill key -> stops imediatelly

# Restart with docker desktop restart
docker run -p 5000:5000 -d --restart=always in28min/todo-rest-api-h2:1.0.0.RELEASE
-> useful for databases

# Events
docker events -> keeps eatching everything that happens in docker

# Top
docker top key
-> see wich process are running in the moment in the container

# Stats
docker stats 
-> technical stats of a running container

# Limit CPU and Memory usage
-m value 
--cpu-quota value (100000 = 100%)

# System df
docker system df
-> general docker system info and details

# --------------------------------------------------------------------------------------------

# Distributed Tracing using zipkin
docker run -p 9411:9411 openzipkin/zipkin:2.23
-> "openzipkin/zipkin:2.23" is the image in docker hub of zipkin, a distribuced tracing system
-> this will trace every thing that happens between all of the microservices

# After setting up all the dependencies on spring to create the image
./mvnw spring-boot:build-image -DskipTests

# Running the image
docker run -p imagePath

# Docker compose
docker-compose up

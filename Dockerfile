#FROM maven:3.6.0-jdk-12-alpine as first_layer
#WORKDIR cont_search_pl
#COPY . /cont_search_pl

#FROM first_layer as second_layer
#RUN mvn -Dmaven.test.skip=true clean package
#CMD java -jar target/persistance-layer-1.0-SNAPSHOT.jar

# Base base build image
FROM maven:3.6.2-jdk-11-slim as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# Build and cache all dependencies
RUN mvn dependency:go-offline -B

# Build our application
COPY ./src ./src
RUN mvn -Dmaven.test.skip=true clean package

# Final base image
FROM openjdk:11-jre-slim

WORKDIR /takeonpl
COPY --from=maven target/persistance-layer-1.0-SNAPSHOT.jar  ./takeonpl/

# set the startup command to run your binary
CMD ["java", "-jar", "./takeonpl/persistance-layer-1.0-SNAPSHOT.jar"]

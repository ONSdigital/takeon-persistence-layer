FROM maven:3.6.0-jdk-12-alpine as first_layer
WORKDIR cont_search_pl
COPY . /cont_search_pl

FROM first_layer as second_layer
RUN mvn -Dmaven.test.skip=true clean package
CMD java -jar target/persistance-layer-1.0-SNAPSHOT.jar
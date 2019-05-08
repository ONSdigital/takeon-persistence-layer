FROM maven:3.6.0-jdk-12-alpine as first_layer
ENV DB_NAME=CollectionDev
# ENV DB_SERVER=sql_server.TakeOnContainerNet
# ENV DB_SERVER=sql-server-db-chart
ENV DB_SERVER=db-layer-takeon
ENV DB_PORT=1433
ENV DATASOURCE_USERNAME=***********
ENV DATASOURCE_PASSWORD=***********
ENV eureka.instance.preferIpAddress=true
ENV AZ_COLLECTION_DB_PASSWORD=***********
ENV AZ_COLLECTION_DB_NAME=collectiondev
ENV AZ_COLLECTION_DB_USER=***********
ENV AZ_COLLECTION_DB_SERVER=takeon.postgres.database.azure.com
ENV PGPASSWORD=***********
WORKDIR cont_search_pl
COPY . /cont_search_pl

FROM first_layer as second_layer
RUN mvn -Dmaven.test.skip=true clean package
CMD java -jar target/persistance-layer-1.0-SNAPSHOT.jar

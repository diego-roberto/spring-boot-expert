FROM adoptopenjdk/openjdk8:jdk8u292-b10-alpine AS MAVEN_BUILD
RUN apk update && apk add --upgrade ttf-dejavu \
    && apk add --no-cache  maven tzdata
ENV TZ=America/Sao_Paulo
COPY pom.xml .
COPY src ./src
CMD mvn package && java -jar target/vendas-api.jar

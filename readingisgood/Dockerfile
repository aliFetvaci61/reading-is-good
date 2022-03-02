FROM openjdk:11 as build

COPY ./target/readingisgood-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch readingisgood-0.0.1-SNAPSHOT.jar'

ARG JAR_FILE=target/readingisgood-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","readingisgood-0.0.1-SNAPSHOT.jar"]
FROM openjdk:17-alpine
RUN apk update
RUN apk add curl
COPY ./target/m4saga-1.0.0.jar /app/m4saga-1.0.0.jar
EXPOSE 8040
ENTRYPOINT ["java","-jar","/app/m4saga-1.0.0.jar"]
FROM openjdk:16-jdk-slim
WORKDIR /home/app
ARG REVISION
COPY target/koneko-guard-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
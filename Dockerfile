
FROM maven:3.8.4-openjdk-17-slim 
RUN echo 'we are building mvn'

# copy the source tree and the pom.xml to our new container
COPY . /app

WORKDIR /app


# package our application code
RUN mvn clean package 

FROM openjdk:17-jdk-slim
COPY . /app
WORKDIR /app/target
RUN echo 'we are running jar'
EXPOSE 9080


# set the startup command to execute the jar
CMD ["java", "-jar", "/app/target/rabbit-client.jar"]
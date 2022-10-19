FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY target/user-service-0.1.jar UserService.jar
ENTRYPOINT ["java","-jar","UserService.jar"]
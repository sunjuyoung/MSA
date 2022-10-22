FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/discoveryService-0.1.jar DiscoveryService.jar
ENTRYPOINT ["java","-jar","DiscoveryService.jar"]
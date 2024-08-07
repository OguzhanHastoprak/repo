FROM openjdk:17
COPY target/*.jar library.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","library.jar"]
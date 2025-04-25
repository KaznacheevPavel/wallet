FROM bellsoft/liberica-openjre-alpine:21.0.5-11
WORKDIR /operations
COPY target/*.jar operations.jar
ENTRYPOINT ["java", "-jar", "operations.jar", "--spring.profiles.active=docker"]
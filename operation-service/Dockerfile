FROM maven:3.9.10-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY lombok.config .
COPY pom.xml .
COPY /auth-service/pom.xml ./auth-service/
COPY /common-lib/pom.xml ./common-lib/
COPY /dependency-bom/pom.xml ./dependency-bom/
COPY /gateway/pom.xml ./gateway/
COPY /operation-service/pom.xml ./operation-service/

RUN mvn dependency:go-offline -B -pl operation-service -am

COPY /operation-service/src ./operation-service/src/
COPY /common-lib/src ./common-lib/src/

RUN mvn clean package -pl operation-service -am -DskipTests

FROM bellsoft/liberica-openjre-alpine:21.0.7

WORKDIR /app

COPY --from=builder ../build/operation-service/target/*.jar /app/operation-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","operation-service.jar"]
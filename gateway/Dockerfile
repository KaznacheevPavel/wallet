FROM maven:3.9.10-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY lombok.config .
COPY pom.xml .
COPY /auth-service/pom.xml ./auth-service/
COPY /common-lib/pom.xml ./common-lib/
COPY /dependency-bom/pom.xml ./dependency-bom/
COPY /gateway/pom.xml ./gateway/

RUN mvn dependency:go-offline -B -pl gateway -am

COPY /gateway/src ./gateway/src/

RUN mvn clean package -pl gateway -am -DskipTests

FROM bellsoft/liberica-openjre-alpine:21.0.7

WORKDIR /app

COPY --from=builder ../build/gateway/target/*.jar /app/gateway.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","gateway.jar"]
FROM maven:3.9.10-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY lombok.config .
COPY pom.xml .
COPY /auth-service/pom.xml ./auth-service/
COPY /common-lib/pom.xml ./common-lib/
COPY /dependency-bom/pom.xml ./dependency-bom/
COPY /gateway/pom.xml ./gateway/

RUN mvn dependency:go-offline -B -pl auth-service -am

COPY /auth-service/src ./auth-service/src/
COPY /common-lib/src ./common-lib/src/

RUN mvn clean package -pl auth-service -am -DskipTests

FROM bellsoft/liberica-openjre-alpine:21.0.7

WORKDIR /app

COPY --from=builder ../build/auth-service/target/*.jar /app/auth-service.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","auth-service.jar"]
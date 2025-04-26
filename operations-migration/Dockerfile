FROM liquibase:4.31
WORKDIR /operations-migration
COPY src/main/resources/db/liquibase-docker.properties liquibase-docker.properties
COPY src/main/resources/db/changelog/. changelog/.
COPY target/*.jar migration.jar
CMD ["--defaultsFile=liquibase-docker.properties","update"]

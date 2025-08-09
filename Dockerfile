# Multi-stage build
# Stage 1: Build the application
FROM registry.access.redhat.com/ubi8/openjdk-17:1.16 AS build

USER root
RUN microdnf install findutils

# Copy maven wrapper and pom.xml
COPY mvnw /code/mvnw
COPY .mvn /code/.mvn
COPY pom.xml /code/

WORKDIR /code

# Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code
COPY src /code/src

# Build the application
RUN ./mvnw package -DskipTests

# Stage 2: Runtime image
FROM registry.access.redhat.com/ubi8/openjdk-17:1.16

ENV LANGUAGE='en_US:en'

# Copy the built application from the build stage
COPY --from=build --chown=185 /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /code/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /code/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

FROM maven:3.6.3-jdk-11-slim AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
COPY src /tmp/src
WORKDIR /tmp/
RUN mvn package

FROM openjdk:11-jre-slim-buster

COPY --from=MAVEN_TOOL_CHAIN /tmp/target/fantasy-football-api*.jar /app.jar

CMD ["java", "-jar", "app.jar"]
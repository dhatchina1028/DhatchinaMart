FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
COPY db ./db
RUN mvn -q clean package -DskipTests

FROM tomcat:9.0-jre17-temurin
COPY --from=build /app/target/dhatchinamart.war /usr/local/tomcat/webapps/dhatchinamart.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

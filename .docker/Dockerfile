FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/benford-analyze.jar app.jar
COPY config config

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "config/config.yml"]

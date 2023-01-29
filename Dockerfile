FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .

RUN ./gradlew bootJar

EXPOSE 8080
EXPOSE 5432

CMD ["java", "-jar", "build/libs/account-0.0.1-SNAPSHOT.jar"]

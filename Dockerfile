FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew build -x test
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar build/libs/*.jar"]

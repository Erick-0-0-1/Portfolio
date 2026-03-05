FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY portfolio_backend /app
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/portfolio-0.0.1-SNAPSHOT.jar"]
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY portfolio_backend /app
RUN chmod +x mvnw
RUN java -version                # <-- add this line
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
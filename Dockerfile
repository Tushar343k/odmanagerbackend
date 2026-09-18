FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 10000

CMD ["sh", "-c", "java -jar target/odmanager-0.0.1-SNAPSHOT.jar --server.address=0.0.0.0 --server.port=${PORT:-10000}"]
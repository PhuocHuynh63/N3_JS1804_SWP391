# Sử dụng hình ảnh JDK từ OpenJDK làm cơ sở
FROM openjdk:17-jdk-alpine

# Sao chép tệp JAR của ứng dụng vào container
COPY target/mebe-0.0.1-SNAPSHOT.jar mebe-0.0.1-SNAPSHOT.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8080

# Biến môi trường (DB_URL, DB_PASSWORD, REDIS_HOST, JWT_SECRET, ...) được truyền lúc chạy,
# xem BE/Project_MeBe/.env.example và docker/docker-compose.yml

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/mebe-0.0.1-SNAPSHOT.jar"]

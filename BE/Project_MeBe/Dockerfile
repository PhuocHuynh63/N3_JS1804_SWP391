# Sử dụng hình ảnh JDK từ OpenJDK làm cơ sở
FROM openjdk:17-jdk-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép tệp JAR của ứng dụng vào container
COPY target/mebe-0.0.1-SNAPSHOT.jar /app/mebe.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app/mebe.jar"]

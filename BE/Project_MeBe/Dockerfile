# Sử dụng hình ảnh JDK từ OpenJDK làm cơ sở
FROM openjdk:17-jdk-alpine

# Sao chép tệp JAR của ứng dụng vào container
COPY target/mebe-0.0.1-SNAPSHOT.jar mebe-0.0.1-SNAPSHOT.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8080

# Đặt các biến môi trường
ENV SPRING_DATASOURCE_URL="jdbc:sqlserver://192.168.1.7:1444;databaseName=me_be_test;encrypt=true;trustServerCertificate=true"
ENV SPRING_DATASOURCE_USERNAME=SA
ENV SPRING_DATASOURCE_PASSWORD=YourStrong@Passw0rd
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.SQLServerDialect

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/mebe-0.0.1-SNAPSHOT.jar"]

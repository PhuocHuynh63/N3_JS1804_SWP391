# Refactor Notes

Nhật ký refactor dự án Me&Be (BE: `BE/Project_MeBe`). Đọc file này để biết đã làm gì, không cần đọc lại code.

## Lộ trình

| Phase | Nội dung | Trạng thái |
|---|---|---|
| 0 | Dọn nền: secrets, constructor injection, enum, BigDecimal, Flyway, tests, đổi tên | 🔄 Đang làm |
| 1 | Java core + JPA (entity, N+1, paging, Specification, @Version) | ⏳ |
| 2 | Spring Security (JWT role/expiry, refresh token, @PreAuthorize, server tính giá) | ⏳ |
| 3 | Clean Architecture (package-by-feature, ports & adapters, modular monolith) | ⏳ |
| 4 | Event-driven (ApplicationEvent → Outbox → Kafka) | ⏳ |
| 5 | Microservices (identity, catalog, order, payment, notification, gateway, saga) | ⏳ |

## Môi trường build

- Branch: `refactor/phase-0` (tách từ `Kumo_clean_code`)
- `./mvnw` lỗi (CRLF trong `.mvn/wrapper/maven-wrapper.properties`). Dùng Maven cache:
  `/c/Users/vipha/.m2/wrapper/dists/apache-maven-3.9.6/834f1afe40575b70b4b2527f4b12df8e/bin/mvn`
- Code gốc KHÔNG compile được: `interceptor/RateLimitInterceptor.java` dòng 16.

## Phase 0 — Checklist

- [ ] 0.1 Sửa lỗi compile `RateLimitInterceptor`
- [ ] 0.2 Sửa `pom.xml` (`maven.compiler.source=-17`), sửa mvnw CRLF
- [ ] 0.3 Secrets → biến môi trường (`application.properties` dùng `${...}`), thêm `.env.example`
- [ ] 0.4 Đổi tên sai chính tả (package `iml`→`impl`, `IProductRespository`, `AddressSerivce`, `CloundinaryService`, `genarateToken`...)
- [ ] 0.5 Field injection → constructor injection (`@RequiredArgsConstructor` + `final`)
- [ ] 0.6 `jakarta.transaction.Transactional` → `org.springframework.transaction.annotation.Transactional`
- [ ] 0.7 CORS: bỏ `@CrossOrigin("*")` từng controller → cấu hình global; URL hardcode → property
- [ ] 0.8 Status string → enum (giữ nguyên giá trị DB/JSON bằng converter)
- [ ] 0.9 Tiền `float` → `BigDecimal`
- [ ] 0.10 Flyway (baseline từ `MeBeTest.sql`)
- [ ] 0.11 Unit tests cho service chính

## Nhật ký chi tiết

(cập nhật sau mỗi bước)

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

- [x] 0.1 Sửa lỗi compile `RateLimitInterceptor` (viết lại: Redis fixed-window 20 req/phút/IP, CHƯA đăng ký vào WebMvc)
- [x] 0.M Tái cấu trúc package → modular monolith (xem "Cấu trúc module" bên dưới)
- [ ] 0.2 Sửa `pom.xml` (`maven.compiler.source=-17`), sửa mvnw CRLF
- [ ] 0.3 Secrets → biến môi trường (`application.properties` dùng `${...}`), thêm `.env.example`; VNPay key trong `payment/config/VNPayConfig`; Redis IP hardcode trong `shared/config/RedisConfig`
- [~] 0.4 Đổi tên sai chính tả — ĐÃ: `iml`→`impl`, `IProductRespository`→`IProductRepository`, `AddressSerivce`→`AddressService`, `CloundinaryService`→`CloudinaryService`, `Config`→`VNPayConfig`, xóa `ApiRespones` (không dùng). CÒN: `genarateToken`
- [ ] 0.5 Field injection → constructor injection (`@RequiredArgsConstructor` + `final`)
- [ ] 0.6 `jakarta.transaction.Transactional` → `org.springframework.transaction.annotation.Transactional`
- [ ] 0.7 CORS: bỏ `@CrossOrigin("*")` từng controller → cấu hình global; URL hardcode → property
- [ ] 0.8 Status string → enum (giữ nguyên giá trị DB/JSON bằng converter)
- [ ] 0.9 Tiền `float` → `BigDecimal`
- [ ] 0.10 Flyway (baseline từ `MeBeTest.sql`)
- [ ] 0.11 Unit tests cho service chính

## Cấu trúc module (sau bước 0.M)

Gốc: `BE/Project_MeBe/src/main/java/com/n3/mebe/`. Mỗi module có `controller / service / service.impl / repository / entity / dto.request / dto.response / mapper`.

| Module | Nội dung |
|---|---|
| `shared` | `config` (GlobalResponseAdvice, ModelMapperConfig, RedisConfig, SchedulerConfig), `exception` (AppException, ErrorCode, GlobalExceptionHandler), `dto` (ApiResponse, ResponseData, TransactionStatusDTO), `util` (DataUtils), `storage` (CloudinaryConfig, ICloudinaryService, CloudinaryService), `web` (RateLimitInterceptor) |
| `notification` | MailConfig, ThymeleafTemplateConfig, ConstEmail, GmailSendResponse, IMailService/MailService, ISendMailService/SendMailService |
| `auth` | LoginController, ForgotPasswordController, ILoginService/LoginService, `security` (CustomFilterSecurity, CustomJwtFilter, CustomUserDetailService, JwtUtilHelper) |
| `user` | User, Address + controller/service/repo/mapper (UserMapper, AddressMapper, GuestMapper, UserOrderMapper, UserProductMapper) |
| `catalog` | Category, SubCategory, Product, Review |
| `order` | Order, OrderDetail (OrderService, OrderDetailsService) |
| `payment` | Payment, VNPayConfig, VNPayService, PaymentService, VnpayController |
| `voucher` | Voucher |
| `wishlist` | WishList |

Script dùng để di chuyển: chạy 1 lần bằng Node (không lưu trong repo). Lưu ý: các module vẫn gọi trực tiếp class của nhau (vd. `order` → `UserService`, `ProductService`) — sẽ cắt phụ thuộc này ở Phase 3.

## Vấn đề phát hiện (để xử lý ở phase sau)

- **Phase 2 / Security:**
  - `CustomJwtFilter` set Authentication rỗng, không role → mọi user có token gọi được API admin. JWT không có `exp`.
  - `/user/signup` bị chặn (security chỉ permit `/user/register` - endpoint không tồn tại).
  - `VNPayService.handleVNPayResponse` KHÔNG kiểm tra chữ ký `vnp_SecureHash` → giả mạo được kết quả thanh toán.
  - Giá/tổng tiền đơn hàng lấy từ client (`OrderRequest.totalAmount`, `item.price`).
  - OTP/mật khẩu tạm lưu Redis với key = chính giá trị OTP (`OTP:123456`) → không gắn với email/user.
  - `@CrossOrigin("*")` ở mọi controller.
- **Phase 1 / JPA:** `@Data` trên entity có quan hệ 2 chiều; `User` EAGER 3 collection; `Order` EAGER orderDetails; N+1 trong ReviewService/UserService; `deleteProduct` set status nhưng không save.
- **Bug nhỏ:** `ProductService.increaseProductQuantity` không set lại "Còn hàng"; `UserService.createUserForAdmin` điều kiện status bị đảo (`isEmpty()`); `AddressService.updateAddress` vòng lặp so sánh sai biến.

## Nhật ký chi tiết

- `3058ede` fix: RateLimitInterceptor compile được + tạo file notes.
- (commit tiếp) refactor: tái cấu trúc package-by-module. `mvn compile` OK.

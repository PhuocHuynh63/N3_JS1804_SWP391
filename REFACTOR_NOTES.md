# Refactor Notes

Nhật ký refactor dự án Me&Be (BE: `BE/Project_MeBe`). Đọc file này để biết đã làm gì, không cần đọc lại code.

## Lộ trình

| Phase | Nội dung | Trạng thái |
|---|---|---|
| 0 | Dọn nền + modular monolith: secrets, constructor injection, enum, BigDecimal, Flyway, tests, đổi tên | ✅ Xong |
| 1 | Java core + JPA (entity, N+1, paging, Specification, @Version) | 🔄 Đang làm (branch `refactor/phase-1` từ `dev`) |
| 2 | Spring Security (JWT role/expiry, refresh token, @PreAuthorize, server tính giá) | ⏳ |
| 3 | Clean Architecture (package-by-feature, ports & adapters, modular monolith) | ⏳ |
| 4 | Event-driven (ApplicationEvent → Outbox → Kafka) | ⏳ |
| 5 | Microservices (identity, catalog, order, payment, notification, gateway, saga) | ⏳ |

## Môi trường build

- Branch: `refactor/phase-0` (tách từ `Kumo_clean_code`)
- `./mvnw` lỗi (CRLF trong `.mvn/wrapper/maven-wrapper.properties`). Dùng Maven cache:
  `/c/Users/vipha/.m2/wrapper/dists/apache-maven-3.9.6/834f1afe40575b70b4b2527f4b12df8e/bin/mvn`
- Code gốc KHÔNG compile được: `interceptor/RateLimitInterceptor.java` dòng 16.

## Lệnh hay dùng (chạy trong `BE/Project_MeBe`)

- Build: `./mvnw -DskipTests package` (hoặc Maven cache ở trên nếu không có mạng)
- Unit test: `./mvnw test`
- Cả integration test (cần Docker): `./mvnw test -DexcludedTestGroups=none`
- Chạy app: cần file `.env` (copy từ `.env.example`), rồi `./mvnw spring-boot:run`

## Phase 0 — Checklist

- [x] 0.1 Sửa lỗi compile `RateLimitInterceptor` (viết lại: Redis fixed-window 20 req/phút/IP, CHƯA đăng ký vào WebMvc)
- [x] 0.M Tái cấu trúc package → modular monolith (xem "Cấu trúc module" bên dưới)
- [x] 0.2 Sửa `pom.xml` (`maven.compiler.source=-17`→`17`); mvnw + wrapper.properties về LF, thêm `BE/Project_MeBe/.gitattributes` (mvnw giờ chạy được, chỉ cần mạng để tải Maven)
- [x] 0.3 Secrets → biến môi trường. `application.properties` dùng `${...}` + `spring.config.import=optional:file:.env[.properties]`. File `BE/Project_MeBe/.env` (git-ignored, đã tạo sẵn trên máy với giá trị cũ) + `.env.example`. VNPay → `payment/config/VNPayProperties` (record `@ConfigurationProperties("vnpay")`) + helper tĩnh `payment/util/VNPayUtils`. `RedisConfig` bỏ IP hardcode (dùng auto-config). Dockerfile/docker-compose bỏ mật khẩu cứng. ⚠️ Secrets cũ vẫn nằm trong lịch sử git → user cần đổi (rotate) mật khẩu Gmail, Cloudinary secret, JWT key, VNPay secret, DB password.
- [x] 0.4 Đổi tên sai chính tả: `iml`→`impl`, `IProductRespository`→`IProductRepository`, `AddressSerivce`→`AddressService`, `CloundinaryService`→`CloudinaryService`, `Config`→`VNPayUtils`, `genarateToken`→`generateToken`, xóa `ApiRespones` (không dùng)
- [x] 0.5 Field injection → constructor injection (`@RequiredArgsConstructor` + `private final`), 31 file. `lombok.config` copy `@Value` sang tham số constructor. `SendMailService` bỏ field trùng (`IUserRepository IUserRepository`)
- [x] 0.6 `jakarta.transaction.Transactional` → Spring `@Transactional`
- [x] 0.7 CORS global: `shared/config/CorsConfig` (bean `CorsConfigurationSource`, đọc `app.cors.allowed-origins`), xóa mọi `@CrossOrigin("*")`. `OrderController` URL cứng → `app.frontend-url`
- [x] 0.8 Status string → enum. Hạ tầng: `shared/enums/LabeledEnum` (label = giá trị DB/JSON cũ, `fromLabel` không phân biệt hoa thường, rỗng→null, sai→IllegalArgumentException) + `LabeledEnumConverter` (JPA). Mỗi enum có `@JsonValue`/`@JsonCreator` + nested `JpaConverter`, entity dùng `@Convert(converter = X.JpaConverter.class)`. `shared/config/WebConfig` cho @RequestParam nhận label. Enum: `catalog.entity.ProductStatus`, `order.entity.OrderStatus` (gồm "Đang giao" FE dùng), `payment.entity.PaymentStatus`, `payment.entity.PaymentMethod` (COD, VNPay, Online + `isOnline()`), `wishlist.entity.WishListStatus`, `user.entity.UserStatus`, `user.entity.UserRole` (admin/staff/member/guest). DTO đổi type tương ứng → JSON API giữ nguyên. GlobalExceptionHandler: JSON/enum sai → 400. Đổi biến `iProductRespository`→`productRepository`.
  - Sửa bug đi kèm: VNPay order trước đây không lưu transactionReference (FE gửi "VNPay", code chỉ check "Online"); `increaseProductQuantity` giờ set lại "Còn hàng"; `deleteProduct` giờ có save; `createUserForAdmin` điều kiện status bị đảo.
  - Nghi vấn giữ nguyên hành vi: `UserService.updateRoleForAdmin` chỉ đổi role khi user ĐANG là admin (có lẽ phải ngược lại) → xem ở Phase 2.
- [x] 0.9 Tiền `float` → `BigDecimal` (DB là DECIMAL(10,2)): Product, OrderDetail, Order, Payment, Voucher (cost/minimumApply/maxDiscount), WishList + mọi DTO tương ứng + filter giá min/max. VNPay: `totalAmount.multiply(100).longValue()`. `Voucher.quantity` vẫn float (không phải tiền). Lưu ý: BigDecimal mặc định null (float mặc định 0).
- [x] 0.10 Flyway (flyway-core + flyway-sqlserver 10.10). `src/main/resources/db/migration/V1__init_schema.sql` (10 bảng) + `V2__seed_data.sql` (dữ liệu mẫu), tách từ `MeBeTest.sql` (file gốc giữ lại để tham khảo). `baseline-on-migrate=true`, `baseline-version=2` → DB cũ bỏ qua V1/V2, DB trống chạy đủ. Thay đổi schema sau này: thêm `V3__...sql`, KHÔNG sửa V1/V2.
- [x] 0.11 Tests: 18 unit test (JUnit5 + Mockito + AssertJ) — `LabeledEnumTest`, `PaymentServiceTest` (có test hồi quy bug VNPay), `OrderServiceTest` (hủy đơn), `ProductServiceTest` (tồn kho), `JwtUtilHelperTest`. Integration test `ProjectMeBeApplicationTests` (Testcontainers SQL Server + Flyway, `@Tag("integration")`, profile `test` với `src/test/resources/application-test.properties`) — mặc định bị loại; CHƯA chạy được vì Docker Desktop chưa bật.

## Phase 1 — Checklist (JPA)

- [x] 1.1 Entity: `@Data` → `@Getter @Setter` (11 entity). Không override equals/hashCode (dùng identity mặc định).
- [x] 1.2 Mọi `@ManyToOne` → LAZY, bỏ EAGER ở User (3 list) và Order.orderDetails. `hibernate.default_batch_fetch_size=50`. `spring.jpa.open-in-view=true` khai báo rõ (mục tiêu sau: tắt). `@Transactional` cho job `ProductService.updateProductStatus`, `WishListService.updateWishListStatus`. User→orders/reviews bỏ cascade ALL (xóa user không xóa lịch sử đơn).
- [x] 1.3 DTO không chứa entity nữa: record `catalog/dto/response/ProductSummaryResponse` (thay `Product` trong ReviewResponse, OrderDetailsResponse, UserReviewResponse), `ProductSubCategoryResponse` (thay `SubCategory` trong ProductResponse, giữ field FE dùng: subCateId, name, slug, image, category.name), `VoucherResponse` thay `Voucher` trong OrderResponse/UserOrderResponse. ModelMapper skip `ProductResponse.subCategory`, map tay trong ProductMapper. `PUT /order/update/orId=`, `PUT /order/refund` trả `OrderResponse` (trước trả entity → đệ quy JSON). Sửa bug: updateOrder với guest tạo `new User()` chưa lưu; refund với code không tồn tại → NPE.
- [ ] 1.4 Sửa N+1 (JOIN FETCH / @EntityGraph) ở list order, review, tracking, wishlist
- [ ] 1.5 Trừ tồn kho bằng UPDATE có điều kiện + @Transactional rollback cả danh sách
- [ ] 1.6 `@Version` (Product, Order) + migration V3 + handler 409
- [ ] 1.7 API phân trang + lọc bằng Specification cho product
- [ ] 1.8 Tests

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
- **Bug nhỏ còn lại:** `AddressService.updateAddress` vòng lặp so sánh sai biến.

## Việc tiếp theo (gợi ý)

1. Bật Docker, chạy `./mvnw test -DexcludedTestGroups=none` để kiểm tra Flyway trên SQL Server thật.
2. User tự đổi (rotate) các secret đã lộ trong lịch sử git.
3. Phase 1 (JPA) hoặc Phase 2 (Security — nhiều lỗ hổng nghiêm trọng, xem mục "Vấn đề phát hiện").

## Nhật ký chi tiết

- `3058ede` fix: RateLimitInterceptor compile được + tạo file notes.
- `b0d7479` refactor: tái cấu trúc package-by-module. `mvn compile` OK.
- `f536c47` chore: secrets→env, constructor injection, CORS global, Spring @Transactional, pom/mvnw fix.
- `e77a193` refactor: status/role/type → enum (0.8).
- `d53a70f` refactor: float → BigDecimal (0.9).
- (commit tiếp) Flyway + tests (0.10, 0.11). Phase 0 xong.

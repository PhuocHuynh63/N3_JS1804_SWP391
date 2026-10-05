package com.n3.mebe.persistence;

import com.n3.mebe.catalog.entity.Category;
import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.catalog.entity.Review;
import com.n3.mebe.catalog.entity.SubCategory;
import com.n3.mebe.catalog.repository.IProductRepository;
import com.n3.mebe.catalog.repository.IReviewRepository;
import com.n3.mebe.order.entity.Order;
import com.n3.mebe.order.entity.OrderDetail;
import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.order.repository.IOrderRepository;
import com.n3.mebe.user.entity.Address;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.user.entity.UserStatus;
import com.n3.mebe.user.repository.IUserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Kiểm tra mapping JPA và số câu SQL thực tế (phát hiện N+1) trên H2 chế độ SQL Server.
 * Không cần Docker. Schema do Hibernate tạo từ entity (Flyway tắt trong test này).
 */
@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:mebe;MODE=MSSQLServer;DATABASE_TO_UPPER=false",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.generate_statistics=true",
        "spring.jpa.show-sql=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryQueryTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IReviewRepository reviewRepository;
    @Autowired
    private IUserRepository userRepository;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        statistics = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
    }

    @Test
    void orderFindAll_loadsUsersInSameQuery() {
        for (int i = 0; i < 3; i++) {
            User user = persistUser("user" + i);
            persistOrder(user, "ORD" + i, OrderStatus.PENDING_CONFIRMATION);
        }
        startCounting();

        List<Order> orders = orderRepository.findAll();
        orders.forEach(o -> o.getUser().getFirstName());

        assertThat(orders).hasSize(3);
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void findWithDetailsByUserId_fetchesDetailsAndProductsInOneQuery() {
        User user = persistUser("buyer");
        Product product = persistProduct("Sữa A");
        for (int i = 0; i < 2; i++) {
            Order order = persistOrder(user, "ORD-D" + i, OrderStatus.PROCESSING);
            persistDetail(order, product);
            persistDetail(order, product);
        }
        startCounting();

        List<Order> orders = orderRepository.findWithDetailsByUserId(user.getUserId());
        orders.forEach(o -> o.getOrderDetails().forEach(d -> d.getProduct().getName()));

        assertThat(orders).hasSize(2); // DISTINCT: không bị nhân bản theo số chi tiết
        assertThat(orders.get(0).getOrderDetails()).hasSize(2);
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void reviewsByProduct_loadUserWithEntityGraph() {
        Product product = persistProduct("Bỉm B");
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setUser(persistUser("reviewer" + i));
            review.setProduct(product);
            review.setComment("ok");
            em.persist(review);
        }
        startCounting();

        List<Review> reviews = reviewRepository.findByProductProductId(product.getProductId());
        reviews.forEach(r -> r.getUser().getFirstName());

        assertThat(reviews).hasSize(3);
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void productFindAll_loadsSubCategoryAndCategory() {
        persistProduct("P1");
        persistProduct("P2");
        startCounting();

        List<Product> products = productRepository.findAll();
        products.forEach(p -> p.getSubCategory().getCategory().getName());

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void userAddresses_areBatchFetched() {
        for (int i = 0; i < 5; i++) {
            User user = persistUser("addr" + i);
            Address address = new Address();
            address.setUser(user);
            address.setAddress("Số " + i);
            em.persist(address);
        }
        startCounting();

        List<User> users = userRepository.findAll();
        users.forEach(u -> u.getListAddress().size());

        // 1 query user + 1 query IN (...) cho tất cả address (thay vì 1 + 5)
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(2);
    }

    @Test
    void enumConverter_storesVietnameseLabel() {
        User user = persistUser("enum");
        Order order = persistOrder(user, "ORD-ENUM", OrderStatus.CANCELLED);
        em.clear();

        Object raw = em.getEntityManager()
                .createNativeQuery("SELECT [status] FROM [order] WHERE order_id = " + order.getOrderId())
                .getSingleResult();
        Order reloaded = orderRepository.findById(order.getOrderId()).orElseThrow();

        assertThat(raw).isEqualTo("Đã hủy");
        assertThat(reloaded.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(userRepository.findById(user.getUserId()).orElseThrow().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    private void startCounting() {
        em.flush();
        em.clear();
        statistics.clear();
    }

    private User persistUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName("First " + username);
        user.setEmail(username + "@example.com");
        user.setStatus(UserStatus.ACTIVE);
        return em.persist(user);
    }

    private Order persistOrder(User user, String code, OrderStatus status) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderCode(code);
        order.setStatus(status);
        order.setTotalAmount(new BigDecimal("100000"));
        return em.persist(order);
    }

    private void persistDetail(Order order, Product product) {
        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(1);
        detail.setPrice(new BigDecimal("50000"));
        em.persist(detail);
    }

    private Product persistProduct(String name) {
        Category category = new Category();
        category.setName("Cat " + name);
        em.persist(category);

        SubCategory subCategory = new SubCategory();
        subCategory.setCategory(category);
        subCategory.setName("Sub " + name);
        em.persist(subCategory);

        Product product = new Product();
        product.setSubCategory(subCategory);
        product.setName(name);
        product.setStatus(ProductStatus.IN_STOCK);
        product.setPrice(new BigDecimal("50000"));
        return em.persist(product);
    }
}

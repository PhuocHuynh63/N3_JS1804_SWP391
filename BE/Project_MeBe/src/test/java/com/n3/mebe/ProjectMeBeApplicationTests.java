package com.n3.mebe;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Khởi động toàn bộ ứng dụng với SQL Server thật (Docker) và chạy Flyway migration.
 * Cần Docker đang chạy. Lệnh: mvn test -DexcludedTestGroups=none
 */
@Tag("integration")
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(classes = ProjectMeBeApplication.class)
class ProjectMeBeApplicationTests {

    @Container
    @ServiceConnection
    static MSSQLServerContainer<?> sqlServer =
            new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2019-latest").acceptLicense();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads_andFlywayCreatesSchemaWithSeedData() {
        Integer categories = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category", Integer.class);
        Integer products = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Integer.class);

        assertThat(categories).isEqualTo(3);
        assertThat(products).isPositive();
    }
}

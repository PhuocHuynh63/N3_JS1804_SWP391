package com.n3.mebe.service.iml;

import com.n3.mebe.dto.request.order.OrderUserCreateRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CreateGuestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTempTable() {
        String createTableSql = "CREATE TABLE #GuestOrderTemp (" +
                "id INT IDENTITY(1,1) PRIMARY KEY, " +
                "firstName NVARCHAR(255), " +
                "lastName NVARCHAR(255), " +
                "email NVARCHAR(255), " +
                "birthOfDate DATE, " +
                "phoneNumber NVARCHAR(50), " +
                "address NVARCHAR(500)" +
                ")";
        jdbcTemplate.execute(createTableSql);
    }

    public void saveOrderUser(OrderUserCreateRequest orderUserCreateRequest) {
        String sql = "INSERT INTO #GuestOrderTemp (firstName, lastName, email, birthOfDate, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderUserCreateRequest.getFirstName(), orderUserCreateRequest.getLastName(), orderUserCreateRequest.getEmail(), orderUserCreateRequest.getBirthOfDate(), orderUserCreateRequest.getPhoneNumber(), orderUserCreateRequest.getAddress());
    }

    public OrderUserCreateRequest getOrderUserById(String email) {
        String sql = "SELECT * FROM #GuestOrderTemp WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new RowMapper<OrderUserCreateRequest>() {
            @Override
            public OrderUserCreateRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderUserCreateRequest orderUser = new OrderUserCreateRequest();

                orderUser.setFirstName(rs.getString("firstName"));
                orderUser.setLastName(rs.getString("lastName"));
                orderUser.setEmail(rs.getString("email"));
                orderUser.setBirthOfDate(rs.getDate("birthOfDate"));
                orderUser.setPhoneNumber(rs.getString("phoneNumber"));
                orderUser.setAddress(rs.getString("address"));
                return orderUser;
            }
        });
    }

    private void dropTemporaryOrderTable() {
        String sql = "DROP TABLE IF EXISTS #GuestOrderTemp";
        jdbcTemplate.execute(sql);
    }

}

package com.palo.market;

import com.palo.market.domain.Customer;
import com.palo.market.domain.Merchant;
import com.palo.market.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;


@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DBInsertTests {


    private final String insertCustomer = "INSERT INTO customer(name, surname, email, address, age, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
    private final String insertMerchant = "INSERT INTO merchant(name, email, address) VALUES (?, ?, ?)";
    private final String insertProduct = "INSERT INTO product(merchant_id, name, description, price, created_at, available) VALUES (?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void crateProduct() {
        Product product = new Product();
        product.setMerchantId(1);
        product.setName("fish");
        product.setDescription("very good fish");
        product.setPrice(9.99);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setAvailable(17);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertProduct);
                ps.setInt(1, product.getMerchantId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                ps.setTimestamp(5, product.getCreatedAt());
                ps.setInt(6, product.getAvailable());
                return ps;
            }
        });

    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setName("Heimdall");
        customer.setSurname("Halfdan");
        customer.setEmail("viking@halfdan");
        customer.setAddress("Kategat");
        customer.setAge(30);
        customer.setPhoneNumber("0915 256 524");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertCustomer);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                ps.setInt(5, customer.getAge());
                ps.setString(6, customer.getPhoneNumber());
                return ps;
            }
        });

    }

    @Test
    public void createMerchant() {

        Merchant merchant = new Merchant();
        merchant.setName("Fish");
        merchant.setEmail("fish@fish");
        merchant.setAddress("Norway");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertMerchant);
                ps.setString(1, merchant.getName());
                ps.setString(2, merchant.getEmail());
                ps.setString(3, merchant.getAddress());
                return ps;
            }
        });

    }
}


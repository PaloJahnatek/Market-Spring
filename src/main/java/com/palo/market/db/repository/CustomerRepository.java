package com.palo.market.db.repository;

import com.palo.market.db.mapper.CustomerRowMapper;
import com.palo.market.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/* CustomerRepository sluzi na komunikaciu s databazou .. nestara sa o biznis logiku */

@Component
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer get(int id) {
        final String sql = "SELECT * FROM customer WHERE customer.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer add(Customer customer) {
        final String sql = " INSERT INTO customer(name, surname, email, address, age, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);//tymto povieme objektu PrepareStatment ye ked nam ho setne v databaze toho customera, tak nech nam este stihne vratit to id ktore vygenerovalo
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                if (customer.getAge() != null) {
                    ps.setInt(5, customer.getAge());
                } else {
                    ps.setNull(5, Types.INTEGER);
                }
                ps.setString(6, customer.getPhoneNumber());
                return ps;
            }
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null; // tymto povieme ze sa to nepodarilo
        }
    }
    public List<Customer> getAll(){
        final String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);//query nam vrati list objektov a customerRowMappeer tak jednotlive rows v tej databaze namapumuje pomocou customerRowMapper a dokopy nam vrati uceleny list customerov
    }
}

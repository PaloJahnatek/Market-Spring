package com.palo.market.db.repository;

import com.palo.market.db.mapper.MerchantRowMapper;
import com.palo.market.domain.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class MerchantRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MerchantRowMapper merchantRowMapper = new MerchantRowMapper();

    @Autowired
    public MerchantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Merchant get(int id) {
        final String sql = "SELECT * FROM merchant WHERE merchant.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, merchantRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer add(Merchant merchant) {
        final String sql = "INSERT INTO merchant(name, email, address) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, merchant.getName());
                ps.setString(2, merchant.getEmail());
                ps.setString(3, merchant.getAddress());
                return ps;
            }
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Merchant> getAll() {
        final String sql = "SELECT * FROM merchant";
        return jdbcTemplate.query(sql, merchantRowMapper);
    }
}

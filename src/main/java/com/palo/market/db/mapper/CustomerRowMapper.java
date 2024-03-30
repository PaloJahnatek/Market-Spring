package com.palo.market.db.mapper;

import com.palo.market.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setAge(rs.getObject("age") == null ? null : rs.getInt("age"));//primitivny typ a int nemoyu byt null preto taky zapis ..string moze byt null
        customer.setPhoneNumber(rs.getString("phone_number"));

        return customer;

    }
}

package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.juliano.carrental.model.Customer;

public class CustomerDao extends Dao<Customer> {
    private final String nameColumnLabel = "name";
    private final String birthDateColumnLabel = "birth_date";
    private final String emailColumnLabel = "email";
    private final String driverLicenseColumnLabel = "driver_license";
    private final String addressColumnLabel = "address";
    private final String phoneNumberColumnLabel = "phone_number";
    private final String createdAtColumnLabel = "created_at";
    private final String updatedAtColumnLabel = "updated_at";

    public CustomerDao(DaoFactory daoFactory) {
        super(daoFactory, "customer", "id");
    }

    @Override
    protected Customer modelFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt(this.idColumnLabel));
        customer.setName(rs.getString(this.nameColumnLabel));
        customer.setBirthDate(rs.getTimestamp(this.birthDateColumnLabel));
        customer.setEmail(rs.getString(this.emailColumnLabel));
        customer.setDriverLicense(rs.getString(this.driverLicenseColumnLabel));
        customer.setAddress(rs.getString(this.addressColumnLabel));
        customer.setPhoneNumber(rs.getString(this.phoneNumberColumnLabel));
        customer.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        customer.setUpdatedAt(rs.getTimestamp(this.updatedAtColumnLabel));
        return customer;
    }

    @Override
    protected int getModelId(Customer customer) {
        return customer.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(Connection con,
            Customer customer) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                this.tableName, this.nameColumnLabel, this.birthDateColumnLabel,
                this.emailColumnLabel, this.driverLicenseColumnLabel, this.addressColumnLabel,
                this.phoneNumberColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, customer.getName());
        ps.setDate(2, new Date(customer.getBirthDate().getTime()));
        ps.setString(3, customer.getEmail());
        ps.setString(4, customer.getDriverLicense());
        ps.setString(5, customer.getAddress());
        ps.setString(6, customer.getPhoneNumber());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Customer customer) throws SQLException {
        // TODO
        return null;
    }

}

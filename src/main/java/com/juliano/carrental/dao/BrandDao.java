package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.juliano.carrental.model.Brand;

public class BrandDao extends Dao<Brand> {
    private final String nameColumnLabel = "name";
    private final String createdAtColumnLabel = "created_at";

    public BrandDao(DaoFactory daoFactory) {
        super(daoFactory, "brand", "id");
    }

    @Override
    protected Brand modelFromResultSet(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getInt(this.idColumnLabel));
        brand.setName(rs.getString(this.nameColumnLabel));
        brand.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        return brand;
    }

    @Override
    protected int getModelId(Brand brand) {
        return brand.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(
            Connection con, Brand brand) throws SQLException {
        String query = String.format("INSERT INTO %s (%s) VALUES (?)",
                this.tableName, this.idColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, brand.getName());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Brand model) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public Brand getByName(String name) {
        ArrayList<Brand> allBrands = super.getAll();

        for (Brand brand : allBrands) {
            if (brand.getName().equals(name)) {
                return brand;
            }
        }
        return null;
    }

    @Override
    public boolean save(Brand obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int update(Brand obj) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(Brand obj) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

}

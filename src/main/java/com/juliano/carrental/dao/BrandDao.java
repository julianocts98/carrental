package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.juliano.carrental.model.Brand;

public class BrandDao implements IDao<Brand> {
    private DaoFactory daoFactory;
    private final String tableName = "brand";
    private final String idColumnLabel = "id";
    private final String nameColumnLabel = "name";
    private final String createdAtColumnLabel = "created_at";

    public BrandDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Brand get(int id) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            Brand brand = new Brand();
            brand.setId(rs.getInt(this.idColumnLabel));
            brand.setName(rs.getString(this.nameColumnLabel));
            brand.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

            return brand;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Brand getByName(String name) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                this.nameColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setString(1, name);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            Brand brand = new Brand();
            brand.setId(rs.getInt(this.idColumnLabel));
            brand.setName(rs.getString(this.nameColumnLabel));
            brand.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

            return brand;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Brand> getAll() {
        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String query = String.format("SELECT * FROM %s;", this.tableName);
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Brand> brands = new ArrayList<Brand>();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt(this.idColumnLabel));
                brand.setName(rs.getString(this.nameColumnLabel));
                brand.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

                brands.add(brand);
            }

            return brands;

        } catch (SQLException e) {
            e.printStackTrace();
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

package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.juliano.carrental.model.Category;

// TODO think better what to do with the catches
public class CategoryDao implements IDao<Category> {
    private DaoFactory daoFactory;
    private final String tableName = "category";
    private final String idColumnLabel = "id";
    private final String nameColumnLabel = "name";
    private final String descriptionColumnLabel = "description";
    private final String createdAtColumnLabel = "created_at";

    public CategoryDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Category get(int id) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            Category category = new Category();
            category.setId(rs.getInt(this.idColumnLabel));
            category.setName(rs.getString(this.nameColumnLabel));
            category.setDescription(rs.getString(this.descriptionColumnLabel));
            // Solve mixing of Date with Timestamps
            // https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
            category.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

            return category;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Category> getAll() {
        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String query = String.format("SELECT * FROM %s;", this.tableName);
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Category> categories = new ArrayList<Category>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(this.idColumnLabel));
                category.setName(rs.getString(this.nameColumnLabel));
                category.setDescription(rs.getString(this.descriptionColumnLabel));
                category.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

                categories.add(category);
            }

            return categories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Category category) {
        String query = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)", this.tableName,
                this.nameColumnLabel, this.descriptionColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement insert = con.prepareStatement(query)) {

            insert.setString(1, category.getName());
            insert.setString(2, category.getDescription());
            insert.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int update(Category category) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", this.tableName, this.nameColumnLabel,
                this.descriptionColumnLabel, this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement updatePstmt = con.prepareStatement(query)) {

            updatePstmt.setString(1, category.getName());
            updatePstmt.setString(2, category.getDescription());
            updatePstmt.setInt(3, category.getId());
            return (updatePstmt.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(Category category) {
        String query = String.format("DELETE FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement deletePstmt = con.prepareStatement(query)) {

            deletePstmt.setInt(1, category.getId());
            deletePstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String query = String.format("DELETE FROM %s", this.tableName);
        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

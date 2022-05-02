package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.juliano.carrental.model.Category;

// TODO think better what to do with the catches
public class CategoryDao extends Dao<Category> {
    private final String nameColumnLabel = "name";
    private final String descriptionColumnLabel = "description";
    private final String createdAtColumnLabel = "created_at";

    public CategoryDao(DaoFactory daoFactory) {
        super(daoFactory, "category", "id");
    }

    @Override
    protected Category modelFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt(this.idColumnLabel));
        category.setName(rs.getString(this.nameColumnLabel));
        category.setDescription(rs.getString(this.descriptionColumnLabel));
        category.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        return category;
    }

    @Override
    protected int getModelId(Category category) {
        return category.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(
            Connection con, Category category) throws SQLException {
        String query = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                this.tableName, this.nameColumnLabel, this.descriptionColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Category category)
            throws SQLException {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", this.tableName, this.nameColumnLabel,
                this.descriptionColumnLabel, this.idColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
        ps.setInt(3, category.getId());
        return ps;
    }

    public Category getByName(String name) {
        return this.getByStringColumn(this.nameColumnLabel, name).get(0);
    }

}

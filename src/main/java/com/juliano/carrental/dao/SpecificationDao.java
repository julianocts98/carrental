package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.juliano.carrental.model.Specification;

public class SpecificationDao implements IDao<Specification> {
    private DaoFactory daoFactory;
    private final String tableName = "specification";
    private final String idColumnLabel = "id";
    private final String nameColumnLabel = "name";
    private final String descriptionColumnLabel = "description";
    private final String createdAtColumnLabel = "created_at";

    public SpecificationDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Specification get(int id) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            Specification specification = new Specification();
            specification.setId(rs.getInt(this.idColumnLabel));
            specification.setName(rs.getString(this.nameColumnLabel));
            specification.setDescription(rs.getString(this.descriptionColumnLabel));
            // Solve mixing of Date with Timestamps
            // https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
            specification.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

            return specification;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Specification> getAll() {
        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String query = String.format("SELECT * FROM %s;", this.tableName);
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Specification> specifications = new ArrayList<Specification>();
            while (rs.next()) {
                Specification specification = new Specification();
                specification.setId(rs.getInt(this.idColumnLabel));
                specification.setName(rs.getString(this.nameColumnLabel));
                specification.setDescription(rs.getString(this.descriptionColumnLabel));
                specification.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));

                specifications.add(specification);
            }

            return specifications;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Specification specification) {
        String query = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)", this.tableName,
                this.nameColumnLabel, this.descriptionColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement insert = con.prepareStatement(query)) {

            insert.setString(1, specification.getName());
            insert.setString(2, specification.getDescription());
            insert.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int update(Specification specification) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", this.tableName, this.nameColumnLabel,
                this.descriptionColumnLabel, this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement updatePstmt = con.prepareStatement(query)) {

            updatePstmt.setString(1, specification.getName());
            updatePstmt.setString(2, specification.getDescription());
            updatePstmt.setInt(3, specification.getId());
            return (updatePstmt.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(Specification specification) {
        String query = String.format("DELETE FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement deletePstmt = con.prepareStatement(query)) {

            deletePstmt.setInt(1, specification.getId());
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

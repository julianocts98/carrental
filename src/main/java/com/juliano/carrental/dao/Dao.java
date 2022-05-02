package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Dao<Model> implements IDao<Model> {
    protected DaoFactory daoFactory;
    protected String tableName;
    protected String idColumnLabel;

    public Dao(DaoFactory daoFactory, String tableName, String idColumnLabel) {
        this.daoFactory = daoFactory;
        this.tableName = tableName;
        this.idColumnLabel = idColumnLabel;
    }

    protected abstract Model modelFromResultSet(ResultSet rs) throws SQLException;

    protected abstract int getModelId(Model model);

    protected abstract PreparedStatement getInsertPreparedStatementWithModelValues(
            Connection con, Model model) throws SQLException;

    protected abstract PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Model model) throws SQLException;

    @Override
    public boolean save(Model model) {
        try (Connection con = this.daoFactory.getConnection();
                PreparedStatement insert = getInsertPreparedStatementWithModelValues(con, model)) {
            insert.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Model get(int id) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {
            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();
            Model model = modelFromResultSet(rs);
            return model;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ArrayList<Model> getByStringColumn(String columnName, String value) {
        ArrayList<Model> models = new ArrayList<Model>();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName,
                columnName);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {
            selectPstmt.setString(1, value);
            ResultSet rs = selectPstmt.executeQuery();
            while (rs.next()) {
                models.add(modelFromResultSet(rs));
            }
            return models;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Model> getAll() {
        String query = String.format("SELECT * FROM %s",
                this.tableName);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {
            ResultSet rs = selectPstmt.executeQuery();
            ArrayList<Model> models = new ArrayList<Model>();
            while (rs.next()) {
                models.add(modelFromResultSet(rs));
            }
            return models;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(Model model) {
        try (Connection con = daoFactory.getConnection();
                PreparedStatement updatePstmt = getUpdatePreparedStatementWithModelValues(con,
                        model)) {
            return (updatePstmt.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(Model model) {
        String query = String.format("DELETE FROM %s WHERE id = ?", this.tableName);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement deletePstmt = con.prepareStatement(query)) {
            deletePstmt.setInt(1, this.getModelId(model));
            deletePstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String query = String.format("DELETE FROM %s", this.tableName);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement deletePstmt = con.prepareStatement(query)) {
            deletePstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.juliano.carrental.model.Specification;

public class SpecificationDao extends Dao<Specification> {
    private final String nameColumnLabel = "name";
    private final String descriptionColumnLabel = "description";
    private final String createdAtColumnLabel = "created_at";

    public SpecificationDao(DaoFactory daoFactory) {
        super(daoFactory, "specification", "id");
    }

    @Override
    protected Specification modelFromResultSet(ResultSet rs) throws SQLException {
        Specification specification = new Specification();
        specification.setId(rs.getInt(this.idColumnLabel));
        specification.setName(rs.getString(this.nameColumnLabel));
        specification.setDescription(rs.getString(this.descriptionColumnLabel));
        specification.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        return specification;
    }

    @Override
    protected int getModelId(Specification specification) {
        return specification.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(Connection con,
            Specification specification) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)", this.tableName,
                this.nameColumnLabel, this.descriptionColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, specification.getName());
        ps.setString(2, specification.getDescription());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Specification specification) throws SQLException {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", this.tableName, this.nameColumnLabel,
                this.descriptionColumnLabel, this.idColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, specification.getName());
        ps.setString(2, specification.getDescription());
        ps.setInt(3, specification.getId());
        return ps;
    }

}

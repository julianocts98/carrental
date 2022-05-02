package com.juliano.carrental.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.juliano.carrental.model.Brand;

public class BrandDaoTest extends DaoTest {

    public BrandDaoTest() {
        super();
    }

    private BrandDao getBrandDao() {
        return new BrandDao(this.daoFactory);
    }

    @AfterEach
    void cleanTable() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "DELETE FROM brand";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGet() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO brand (name) VALUES ('TOYOTA')";
            stmt.execute(query);

            query = "SELECT id FROM brand WHERE name = 'TOYOTA'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id = rs.getInt("id");

            Brand brand = this.daoFactory.getBrandDao().get(id);

            assertEquals("TOYOTA", brand.getName());

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testGetAll() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO brand (name) VALUES ('TOYOTA')";
            stmt.execute(query);

            query = "INSERT INTO brand (name) VALUES ('NISSAN')";
            stmt.execute(query);

            query = "INSERT INTO brand (name) VALUES ('VOLKSWAGEN')";
            stmt.execute(query);

            ArrayList<Brand> brands = this.daoFactory.getBrandDao().getAll();

            assertEquals(3, brands.size());

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

}

package com.juliano.carrental.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.juliano.carrental.model.Category;

public class CategoryDaoTest extends DaoTest {

    public CategoryDaoTest() {
        super();
    }

    /**
     * rs needs to be a ResultSet with all the columns in the Category table.
     */
    private Category categoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setCreatedAt(rs.getTimestamp("created_at"));
        return category;
    }

    private CategoryDao getCategoryDao() {
        return new CategoryDao(this.daoFactory);
    }

    @AfterEach
    void cleanDb() throws SQLException {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "DELETE FROM category;";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSave() {
        Category category = new Category();
        category.setName("SUV");
        category.setDescription("Carro Grandão");

        getCategoryDao().save(category);

        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "select * from category where name='SUV';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            String name = rs.getString("name");
            String description = rs.getString("description");

            assertAll("category",
                    () -> assertEquals("SUV", name),
                    () -> assertEquals("Carro Grandão", description));

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testGet() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('SUV', 'Carro Grandão', '2022-04-30 15:43:00 -3:00');";
            stmt.execute(query);

            query = "SELECT id FROM category WHERE name='SUV';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int idFromQuery = rs.getInt("id");

            Category category = getCategoryDao().get(idFromQuery);

            assertAll("category",
                    () -> assertEquals(idFromQuery, category.getId()),
                    () -> assertEquals("SUV", category.getName()),
                    () -> assertEquals("Carro Grandão", category.getDescription()),
                    () -> assertEquals(DaoFactory.parseTimestamp("2022-04-30 15:43:00 -03:00"),
                            category.getCreatedAt()));

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testGetAll() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('SUV', 'Carro Grandão', '2022-04-30 15:43:00 -3:00');";
            stmt.execute(query);

            query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('Caminhonete', 'Carro Grandão', '2022-04-30 15:45:00 -3:00');";
            stmt.execute(query);

            ArrayList<Category> categories = getCategoryDao().getAll();

            Category category1 = categories.get(0);
            Category category2 = categories.get(1);

            assertAll("categories",
                    () -> assertEquals("SUV", category1.getName()),
                    () -> assertEquals("Carro Grandão", category1.getDescription()),
                    () -> assertEquals(DaoFactory.parseTimestamp("2022-04-30 15:43:00 -03:00"),
                            category1.getCreatedAt()),

                    () -> assertEquals("Caminhonete", category2.getName()),
                    () -> assertEquals("Carro Grandão", category2.getDescription()),
                    () -> assertEquals(DaoFactory.parseTimestamp("2022-04-30 15:45:00 -03:00"),
                            category2.getCreatedAt()));

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testUpdate() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('SUV', 'Carro Grandão', '2022-04-30 15:43:00 -3:00');";
            stmt.execute(query);

            query = "SELECT * FROM category WHERE name='SUV';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            Category category = this.categoryFromResultSet(rs);

            category.setName("Caminhonete");
            category.setDescription("Carro grande com caçamba atrás");
            getCategoryDao().update(category);

            query = "SELECT * FROM category WHERE name='Caminhonete';";
            rs = stmt.executeQuery(query);
            rs.next();
            Category updatedCategory = this.categoryFromResultSet(rs);

            assertAll("changed",
                    () -> assertEquals(category.getId(), updatedCategory.getId()),
                    () -> assertEquals("Caminhonete", updatedCategory.getName()),
                    () -> assertEquals("Carro grande com caçamba atrás",
                            updatedCategory.getDescription()),
                    () -> assertEquals(category.getCreatedAt(), updatedCategory.getCreatedAt()));

        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testDelete() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('SUV', 'Carro Grandão', '2022-04-30 15:43:00 -3:00');";
            stmt.execute(query);

            query = "SELECT * FROM category WHERE name='SUV';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            Category category = this.categoryFromResultSet(rs);

            getCategoryDao().delete(category);

            query = "SELECT * FROM category;";
            rs = stmt.executeQuery(query);
            assertFalse(rs.next());
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void testDeleteAll() {
        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {
            String query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('SUV', 'Carro Grandão', '2022-04-30 15:43:00 -3:00');";
            stmt.execute(query);

            query = "INSERT INTO category (name, description, created_at) " +
                    "VALUES ('Caminhonete', 'Carro Grandão', '2022-04-30 15:45:00 -3:00');";
            stmt.execute(query);

            getCategoryDao().deleteAll();

            query = "SELECT * FROM category;";
            ResultSet rs = stmt.executeQuery(query);

            assertFalse(rs.next());

        } catch (SQLException e) {
            fail(e.toString());
        }
    }
}

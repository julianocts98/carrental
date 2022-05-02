package com.juliano.carrental.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Category;
import com.juliano.carrental.model.Specification;

public class CarDaoTest {

    private DaoFactory daoFactory;

    public CarDaoTest() {
        String url = "jdbc:postgresql://localhost:5432/testCarRental";
        String user = "postgres";
        String password = "password";
        this.daoFactory = new DaoFactory(new DataSource(url, user, password));
    }

    private CarDao getCarDao() {
        return new CarDao(this.daoFactory);
    }

    private Brand brandFromId(int id) {
        Brand brand = new Brand();
        String query = "SELECT * from BRAND WHERE id = ?";

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            brand.setId(rs.getInt("id"));
            brand.setName(rs.getString("name"));
            brand.setCreatedAt(rs.getTimestamp("created_at"));
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Category categoryFromId(int id) {
        Category category = new Category();
        String query = "SELECT * from category WHERE id = ?";

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            rs.next();

            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            category.setCreatedAt(rs.getTimestamp("created_at"));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Specification> specificationsFromCarId(int id) {
        ArrayList<Specification> specifications = new ArrayList<Specification>();
        String query = "SELECT * FROM specification s WHERE s.id IN" +
                "(SELECT cs.specification_id FROM cars_specification cs WHERE cs.car_id = ?);";

        try (Connection con = daoFactory.getConnection();
                PreparedStatement selectPstmt = con.prepareStatement(query)) {

            selectPstmt.setInt(1, id);
            ResultSet rs = selectPstmt.executeQuery();
            while (rs.next()) {
                Specification specification = new Specification();
                specification.setId(rs.getInt("id"));
                specification.setName(rs.getString("name"));
                specification.setDescription(rs.getString("description"));
                specification.setCreatedAt(rs.getTimestamp("created_at"));
                specifications.add(specification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specifications;
    }

    private Car carFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setName(rs.getString("name"));
        car.setDescription(rs.getString("description"));
        car.setDailyRate(rs.getInt("daily_rate"));
        car.setAvailable(rs.getBoolean("available"));
        car.setLicensePlate(rs.getString("license_plate"));
        car.setBrand(this.brandFromId(rs.getInt("brand_id")));
        car.setCategory(this.categoryFromId(rs.getInt("category_id")));
        car.setColor(rs.getString("color"));
        car.setSpecifications(this.specificationsFromCarId(rs.getInt("id")));
        car.setCreatedAt(rs.getTimestamp("created_at"));
        return car;
    }

    @BeforeEach
    void populateDependentTables() {
        String queryFormat = "";
        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            // Insert "mock" data into category table;
            queryFormat = "INSERT INTO category (name, description, created_at) VALUES (%s)";
            String[] categoryQueriesValues = {
                    "'Sedan', 'Carro com porta-mala protuberante', '2022-05-01 00:14:15.000'",
                    "'HATCH', 'Carro porta-malas mais interno', '2022-05-01 00:15:07.000'",
                    "'SUV', 'Veículo utilitário esportivo', '2022-05-01 10:33:27.721'"
            };
            for (String queryValue : categoryQueriesValues) {
                String query = String.format(queryFormat, queryValue);
                stmt.execute(query);
            }

            // Insert "mock" data into brand table
            queryFormat = "INSERT INTO brand (name, created_at) VALUES(%s)";
            String[] brandQueriesValues = {
                    "'Volkswagen', '2022-04-30 13:16:00.000'",
                    "'Nissan', '2022-05-01 14:11:54.791'",
                    "'Chery', '2022-05-01 14:11:54.806'",
                    "'Citroen', '2022-05-01 14:11:54.817'",
                    "'Honda', '2022-05-01 14:11:54.827'"
            };
            for (String queryValue : brandQueriesValues) {
                String query = String.format(queryFormat, queryValue);
                stmt.execute(query);
            }

            // Insert "mock" data into specification table
            queryFormat = "INSERT INTO specification (name, description, created_at) VALUES(%s)";
            String[] specificationQueriesValues = {
                    "'Automático', 'Carro com câmbio automático', '2022-05-01 11:49:19.943'",
                    "'Híbrido', 'Carro movido a combustível líquido e eletricidade', '2022-05-01 12:03:27.906'",
                    "'Manual', 'Carro com câmbio manual', '2022-05-01 13:08:24.895'",
                    "'Diesel', 'Carro movido a diesel', '2022-05-01 13:08:35.651'",
                    "'Gasolina', 'Carro movido a gasolina', '2022-05-01 13:08:45.749'",
                    "'Etanol', 'Carro movido a etanol', '2022-05-01 13:08:54.518'",
                    "'Flex', 'Pode usar gasolina ou etanol', '2022-05-01 13:09:03.620'",
                    "'4 portas', 'Carro possui 4 portas', '2022-05-01 13:09:16.172'",
                    "'Airbag', 'Carro possui sistema de airbag', '2022-05-01 13:09:28.291'",
                    "'2 portas', 'Carro com duas portas', '2022-05-01 13:09:35.377'",
                    "'Mídia digital', 'Carro com sistema de mídia digital', '2022-05-01 13:09:42.571'",
                    "'Alarme', 'Carro com alarme de fábrica', '2022-05-01 13:10:29.152'",
            };
            for (String queryValue : specificationQueriesValues) {
                String query = String.format(queryFormat, queryValue);
                stmt.execute(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void clearDependentTables() {
        String queryFormat = "DELETE FROM %s";

        try (Connection con = daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String[] tableNames = { "car_image", "car", "category", "brand", "specification" };
            for (String table : tableNames) {
                String query = String.format(queryFormat, table);
                stmt.execute(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSave() {
        Car car = new Car();
        car.setName("Jetta");
        car.setDescription("Carro 2012");
        car.setAvailable(true);
        car.setLicensePlate("ABC-1234");

        Brand brand = new Brand();
        brand.setName("Volkswagen");
        car.setBrand(brand);

        Category category = new Category();
        category.setName("Sedan");
        car.setCategory(category);

        car.setColor("Prata");
        int dailyRate = (int) Math.round(50.00 * 100);
        car.setDailyRate(dailyRate);

        this.getCarDao().save(car);

        try (Connection con = this.daoFactory.getConnection();
                Statement stmt = con.createStatement()) {

            String query = "SELECT * FROM car";
            ResultSet rs = stmt.executeQuery(query);
            assertTrue(rs.next());

        } catch (SQLException e) {
            fail(e.toString());
        }

    }

}

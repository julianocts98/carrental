package com.juliano.carrental.dabatase;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    private final String URL = "jdbc:postgresql://localhost:5432/testCarRental";
    private final String USER = "postgres";
    private final String PASSWORD = "password";
    private Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        this.con = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);

    }

    @Test
    void testDatabaseStatmentExecuteQuery() {
        try (Statement stmt = this.con.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from brand;");
            rs.next();

        } catch (SQLException e) {
            fail(e.toString());
        }
    }
}

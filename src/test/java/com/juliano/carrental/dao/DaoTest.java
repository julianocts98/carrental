package com.juliano.carrental.dao;

public class DaoTest {
    protected DaoFactory daoFactory;

    public DaoTest() {
        String url = "jdbc:postgresql://localhost:5432/testCarRental";
        String user = "postgres";
        String password = "password";
        this.daoFactory = new DaoFactory(new DataSource(url, user, password));
    }
}

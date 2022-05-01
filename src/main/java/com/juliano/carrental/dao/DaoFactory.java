package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaoFactory {
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss X";
    private DataSource dataSource;

    public DaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static String formatTimestamp(Date timestamp) {
        return new SimpleDateFormat(DaoFactory.TIMESTAMP_PATTERN).format(timestamp);
    }

    public static Date parseTimestamp(String timestamp) throws ParseException {
        return new SimpleDateFormat(DaoFactory.TIMESTAMP_PATTERN).parse(timestamp);
    }

    public CategoryDao getCategoryDao() {
        return new CategoryDao(this);
    }

}

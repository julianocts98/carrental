package com.juliano.carrental;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.dao.DataSource;
import com.juliano.carrental.gui.CreateCategoryFrame;

public class App {
    private static DaoFactory daoFactory;
    static {
        String url = "jdbc:postgresql://localhost:5432/carRental";
        String user = "postgres";
        String password = "password";
        daoFactory = new DaoFactory(new DataSource(url, user, password));
    }

    public static void main(String[] args) {

        CreateCategoryFrame frame = new CreateCategoryFrame(daoFactory);
        frame.setVisible(true);
    }
}

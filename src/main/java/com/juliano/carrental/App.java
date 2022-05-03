package com.juliano.carrental;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.dao.DataSource;
import com.juliano.carrental.gui.MainFrame;

public class App {
    private static DaoFactory daoFactory;

    static {
        String url = "jdbc:postgresql://localhost:5432/car_rental";
        String user = "postgres";
        String password = "password";
        daoFactory = new DaoFactory(new DataSource(url, user, password));
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(daoFactory);
        frame.setVisible(true);
    }
}

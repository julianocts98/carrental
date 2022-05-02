package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Category;

public class CarDao extends Dao<Car> {
    private final String carImageTableName = "car_image";

    private final String nameColumnLabel = "name";
    private final String descriptionColumnLabel = "description";
    private final String dailyRateColumnLabel = "daily_rate";
    private final String availableColumnLabel = "available";
    private final String licensePlateColumnLabel = "license_plate";
    private final String brandIdColumnLabel = "brand_id";
    private final String categoryIdColumnLabel = "category_id";
    private final String colorColumnLabel = "color";
    private final String createdAtColumnLabel = "created_at";

    public CarDao(DaoFactory daoFactory) {
        super(daoFactory, "car", "id");
    }

    @Override
    protected Car modelFromResultSet(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int getModelId(Car model) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(Connection con, Car model)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con, Car model)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(Car car) {
        Brand brand = this.daoFactory.getBrandDao().getByName(car.getBrand().getName());
        Category category = this.daoFactory.getCategoryDao().getByName(car.getCategory().getName());

        String insertCarQuery = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                this.tableName, this.nameColumnLabel, this.descriptionColumnLabel,
                this.dailyRateColumnLabel, this.availableColumnLabel, this.licensePlateColumnLabel,
                this.brandIdColumnLabel, this.categoryIdColumnLabel, this.colorColumnLabel);

        String insertCarImageQuery = "INSERT INTO car_image (car_id, image) VALUES (?, ?)";
        String selectCarIdByLicensePlate = "SELECT id FROM car WHERE license_plate = ?";

        // TODO rollback if any execute fails
        try (Connection con = daoFactory.getConnection();
                PreparedStatement insertCar = con.prepareStatement(insertCarQuery);
                PreparedStatement insertCarImage = con.prepareStatement(insertCarImageQuery);
                PreparedStatement selectCarId = con.prepareStatement(selectCarIdByLicensePlate)) {

            con.setAutoCommit(false);

            insertCar.setString(1, car.getName());
            insertCar.setString(2, car.getDescription());
            insertCar.setInt(3, car.getDailyRate());
            insertCar.setBoolean(4, car.isAvailable());
            insertCar.setString(5, car.getLicensePlate());
            insertCar.setInt(6, brand.getId());
            insertCar.setInt(7, category.getId());
            insertCar.setString(8, car.getColor());
            insertCar.execute();

            selectCarId.setString(1, car.getLicensePlate());
            ResultSet rs = selectCarId.executeQuery();
            rs.next();
            int id = rs.getInt("id");

            insertCarImage.setInt(1, id);
            insertCarImage.setBytes(2, car.getImage());
            insertCarImage.execute();

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int update(Car obj) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(Car obj) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

}

package com.juliano.carrental.dao;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Category;
import com.juliano.carrental.model.Specification;

public class CarDao extends Dao<Car> {
    private final String carImageTableName = "car_image";
    private final String carImageColumnLabel = "image";
    private final String carImageCarIdColumnLabel = "car_id";

    private final String carSpecificationTable = "car_specification";
    private final String carSpecificationCarIdColumnLabel = "car_id";
    private final String carSpecificationIdColumnLabel = "specification_id";

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
        Car car = new Car();
        car.setId(rs.getInt(this.idColumnLabel));
        car.setName(rs.getString(this.nameColumnLabel));
        car.setDescription(rs.getString(this.descriptionColumnLabel));
        car.setDailyRate(rs.getInt(this.dailyRateColumnLabel));
        car.setAvailable(rs.getBoolean(this.availableColumnLabel));
        car.setLicensePlate(rs.getString(this.licensePlateColumnLabel));

        Brand brand = this.daoFactory.getBrandDao().get(rs.getInt(this.brandIdColumnLabel));
        car.setBrand(brand);

        Category category = this.daoFactory.getCategoryDao()
                .get(rs.getInt(this.categoryIdColumnLabel));
        car.setCategory(category);

        ArrayList<Specification> specifications = this.getCarSpecifications(car);

        car.setColor(rs.getString(this.colorColumnLabel));
        car.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        return car;
    }

    @Override
    protected int getModelId(Car car) {
        return car.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(Connection con, Car car)
            throws SQLException {
        Brand brand = this.daoFactory.getBrandDao().getByName(car.getBrand().getName());
        Category category = this.daoFactory.getCategoryDao().getByName(car.getCategory().getName());

        String insertCarQuery = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                this.tableName, this.nameColumnLabel, this.descriptionColumnLabel,
                this.dailyRateColumnLabel, this.availableColumnLabel, this.licensePlateColumnLabel,
                this.brandIdColumnLabel, this.categoryIdColumnLabel, this.colorColumnLabel);
        PreparedStatement ps = con.prepareStatement(insertCarQuery);
        ps.setString(1, car.getName());
        ps.setString(2, car.getDescription());
        ps.setInt(3, car.getDailyRate());
        ps.setBoolean(4, car.isAvailable());
        ps.setString(5, car.getLicensePlate());
        ps.setInt(6, brand.getId());
        ps.setInt(7, category.getId());
        ps.setString(8, car.getColor());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con, Car model)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(Car car) {
        super.save(car);
        String insertCarImageQuery = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)", this.carImageTableName,
                this.carImageCarIdColumnLabel, this.carImageColumnLabel);
        String selectCarIdByLicensePlateQuery = String
                .format("SELECT %s FROM %s WHERE %s = ?", this.idColumnLabel, this.tableName,
                        this.licensePlateColumnLabel);
        String insertCarSpecificationsQuery = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)",
                this.carSpecificationTable, this.carSpecificationCarIdColumnLabel,
                this.carSpecificationIdColumnLabel);
        try (Connection con = daoFactory.getConnection();
                PreparedStatement insertCarImage = con.prepareStatement(insertCarImageQuery);
                PreparedStatement selectCarId = con
                        .prepareStatement(selectCarIdByLicensePlateQuery);
                PreparedStatement insertCarSpecification = con
                        .prepareStatement(insertCarSpecificationsQuery)) {
            con.setAutoCommit(false);

            selectCarId.setString(1, car.getLicensePlate());
            ResultSet rs = selectCarId.executeQuery();
            rs.next();
            int id = rs.getInt("id");

            insertCarImage.setInt(1, id);
            insertCarImage.setBytes(2, car.getImage());
            insertCarImage.execute();

            insertCarSpecification.setInt(1, id);
            for (Specification specification : car.getSpecifications()) {
                insertCarSpecification.setInt(2, specification.getId());
                insertCarSpecification.execute();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Car get(int id) {
        Car car = super.get(id);
        loadCarImage(car);
        return car;
    }

    @Override
    public ArrayList<Car> getAll() {
        ArrayList<Car> allCars = super.getAll();
        for (Car car : allCars) {
            car.setSpecifications(this.getCarSpecifications(car));
        }
        return allCars;
    }

    public ArrayList<Specification> getCarSpecifications(Car car) {
        ArrayList<Specification> specifications = new ArrayList<Specification>();
        String selectCarSpecificationIdsQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                this.carSpecificationIdColumnLabel, this.carSpecificationTable,
                this.carSpecificationCarIdColumnLabel);
        ;
        try (Connection con = daoFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(selectCarSpecificationIdsQuery)) {
            ps.setInt(1, car.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Specification specification = this.daoFactory.getSpecificationDao()
                        .get(rs.getInt(this.carSpecificationIdColumnLabel));
                specifications.add(specification);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specifications;
    }

    public ArrayList<Car> getAvailableCars() {
        ArrayList<Car> cars = getAll();
        cars.removeIf(c -> !c.isAvailable());
        return cars;
    }

    public int rentACar(Car car) {
        String updateCarQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?", this.tableName,
                this.availableColumnLabel, this.idColumnLabel);

        try (Connection con = daoFactory.getConnection();
                PreparedStatement updateCar = con.prepareStatement(updateCarQuery)) {
            return updateCar.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    private void loadCarImage(Car car) {
        String getImageQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                this.carImageColumnLabel, this.carImageTableName, this.carImageCarIdColumnLabel);
        try (Connection con = daoFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(getImageQuery)) {
            con.setAutoCommit(false);
            ps.setInt(1, car.getId());

            ResultSet rs = ps.executeQuery();
            rs.next();
            car.setImage(rs.getBytes(this.carImageColumnLabel));
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon getImageIconByCar(Car car, int width, int height) {
        loadCarImage(car);
        ImageIcon imageIcon = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(car.getImage());
            Image image = ImageIO.read(bais).getScaledInstance(width, height,
                    java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image); // transform it back
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

}

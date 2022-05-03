package com.juliano.carrental.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Customer;
import com.juliano.carrental.model.Rental;

public class RentalDao extends Dao<Rental> {
    private final String carIdColumnLabel = "car_id";
    private final String customerIdColumnLabel = "customer_id";
    private final String startDateColumnLabel = "start_date";
    private final String endDateColumnLabel = "end_date";
    private final String totalColumnLabel = "total";
    private final String createdAtColumnLabel = "created_at";
    private final String updatedAtColumnLabel = "updated_at";

    public RentalDao(DaoFactory daoFactory) {
        super(daoFactory, "rental", "id");
    }

    @Override
    protected Rental modelFromResultSet(ResultSet rs) throws SQLException {
        Rental rental = new Rental();
        rental.setId(rs.getInt(this.idColumnLabel));

        Car car = this.daoFactory.getCarDao().get(rs.getInt(this.carIdColumnLabel));
        rental.setCar(car);

        Customer customer = this.daoFactory.getCustomerDao()
                .get(rs.getInt(this.customerIdColumnLabel));
        rental.setCustomer(customer);

        rental.setStartDate(rs.getTimestamp(this.startDateColumnLabel));
        rental.setEndDate(rs.getTimestamp(this.endDateColumnLabel));
        rental.setTotal(rs.getInt(this.totalColumnLabel));
        rental.setCreatedAt(rs.getTimestamp(this.createdAtColumnLabel));
        rental.setUpdatedAt(rs.getTimestamp(this.updatedAtColumnLabel));
        return rental;
    }

    @Override
    public boolean save(Rental rental) {
        if (!super.save(rental)) {
            return false;
        }
        this.daoFactory.getCarDao().rentACar(rental.getCar());
        return true;
    }

    @Override
    protected int getModelId(Rental rental) {
        return rental.getId();
    }

    @Override
    protected PreparedStatement getInsertPreparedStatementWithModelValues(Connection con,
            Rental rental) throws SQLException {
        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                this.tableName, this.carIdColumnLabel, this.customerIdColumnLabel,
                this.startDateColumnLabel, this.endDateColumnLabel, this.totalColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, rental.getCar().getId());
        ps.setInt(2, rental.getCustomer().getId());
        ps.setDate(3, new Date(rental.getStartDate().getTime()));
        ps.setDate(4, new Date(rental.getEndDate().getTime()));
        ps.setInt(5, rental.getTotal());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePreparedStatementWithModelValues(Connection con,
            Rental rental) throws SQLException {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = current_timestamp WHERE %s = ?", this.tableName,
                this.endDateColumnLabel,
                this.totalColumnLabel, this.idColumnLabel, this.updatedAtColumnLabel);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setDate(1, new Date(rental.getEndDate().getTime()));
        ps.setInt(2, rental.getTotal());
        ps.setInt(3, rental.getId());
        return ps;
    }

    public int getTotalRentalValueByCalendars(Car car, GregorianCalendar startCalendar,
            GregorianCalendar endCalendar) {
        long diffInMilis = endCalendar.getTimeInMillis()
                - startCalendar.getTimeInMillis();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMilis);
        if (days <= 0) {
            return -1;
        }
        return car.getDailyRate() * days;
    }

}

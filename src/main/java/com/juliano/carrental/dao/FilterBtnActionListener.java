package com.juliano.carrental.dao;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.gui.CreateRentalFrame;
import com.juliano.carrental.model.Car;

public class FilterBtnActionListener implements Action {
    private CreateRentalFrame frame;
    private DaoFactory daoFactory;
    private JTable availableCarsTable;
    private ArrayList<Car> filteredCars;
    private JComboBox customerComboBox;
    private JComboBox brandComboBox;
    private JComboBox categoryComboBox;
    private JDatePicker startDatePicker;
    private JDatePicker endDatePicker;

    private JTextField carNameTextField;
    private JTextField carColorTextField;
    private JTextField carDescriptionTextField;
    private JTextField carDailyRateTextField;
    private JTextField carBrandTextField;
    private JTextField carCategoryTextField;
    private JList carSpecificationsJList;
    private JLabel carImageLabel;
    private JTextField rentalTotalValueTextField;

    public FilterBtnActionListener(CreateRentalFrame frame, DaoFactory daoFactory,
            JTable availableCarsTable, JComboBox customerComboBox, JComboBox brandComboBox,
            JComboBox categoryComboBox, JDatePicker startDatePicker, JDatePicker endDatePicker,
            JTextField carNameTextField, JTextField carColorTextField,
            JTextField carDescriptionTextField, JTextField carDailyRateTextField,
            JTextField carBrandTextField, JTextField carCategoryTextField,
            JList carSpecificationsJList, JLabel carImageLabel,
            JTextField rentalTotalValueTextField) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.availableCarsTable = availableCarsTable;
        this.customerComboBox = customerComboBox;
        this.brandComboBox = brandComboBox;
        this.categoryComboBox = categoryComboBox;
        this.startDatePicker = startDatePicker;
        this.endDatePicker = endDatePicker;

        this.carNameTextField = carNameTextField;
        this.carColorTextField = carColorTextField;
        this.carDescriptionTextField = carDescriptionTextField;
        this.carDailyRateTextField = carDailyRateTextField;
        this.carBrandTextField = carBrandTextField;
        this.carCategoryTextField = carCategoryTextField;
        this.carSpecificationsJList = carSpecificationsJList;
        this.carImageLabel = carImageLabel;
        this.rentalTotalValueTextField = rentalTotalValueTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.setAvailableCars(this.daoFactory.getCarDao().getAvailableCars());
        this.frame.clearCarFields();
        this.filteredCars = new ArrayList<Car>(this.frame.getAvailableCars());
        if (this.brandComboBox.getSelectedIndex() != 0) {
            String selectedBrandName = (String) this.brandComboBox.getSelectedItem();
            this.filteredCars.removeIf(car -> !car.getBrand().getName().equals(selectedBrandName));
        }
        if (this.categoryComboBox.getSelectedIndex() != 0) {
            String selectedCategoryName = (String) this.categoryComboBox.getSelectedItem();
            this.filteredCars
                    .removeIf(car -> !car.getCategory().getName().equals(selectedCategoryName));
        }
        this.frame.setFilteredCars(filteredCars);
        if (this.filteredCars.isEmpty()) {
            this.frame.setSelectedCar(null);
        }
        frame.populateAvailableCarsTable(availableCarsTable, filteredCars);

    }

    @Override
    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setEnabled(boolean b) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // TODO Auto-generated method stub

    }

}

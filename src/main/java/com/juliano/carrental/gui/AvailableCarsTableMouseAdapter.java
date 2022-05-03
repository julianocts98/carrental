package com.juliano.carrental.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Car;

public class AvailableCarsTableMouseAdapter extends MouseAdapter {
    private CreateRentalFrame frame;
    private DaoFactory daoFactory;
    private JTable availableCarsTable;
    private JTextField carNameTextField;
    private JTextField carColorTextField;
    private JTextField carDescriptionTextField;
    private JTextField carDailyRateTextField;
    private JTextField carBrandTextField;
    private JTextField carCategoryTextField;
    private JList carSpecificationsJList;
    private JLabel carImageLabel;
    private JTextField rentalTotalValueTextField;
    private JDatePicker endDatePicker;

    public AvailableCarsTableMouseAdapter(CreateRentalFrame frame, DaoFactory daoFactory,
            JTable availableCarsTable, JTextField carNameTextField,
            JTextField carColorTextField, JTextField carDescriptionTextField,
            JTextField carDailyRateTextField, JTextField carBrandTextField,
            JTextField carCategoryTextField, JList carSpecificationsJList, JLabel carImageLabel,
            JTextField rentalTotalValue, JDatePicker endDatePicker) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.availableCarsTable = availableCarsTable;
        this.carNameTextField = carNameTextField;
        this.carColorTextField = carColorTextField;
        this.carDescriptionTextField = carDescriptionTextField;
        this.carDailyRateTextField = carDailyRateTextField;
        this.carBrandTextField = carBrandTextField;
        this.carCategoryTextField = carCategoryTextField;
        this.carSpecificationsJList = carSpecificationsJList;
        this.carImageLabel = carImageLabel;
        this.rentalTotalValueTextField = rentalTotalValue;
        this.endDatePicker = endDatePicker;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int index = this.availableCarsTable.getSelectedRow();
        if (index == -1 || this.availableCarsTable.getRowCount() == 0) {
            this.frame.setSelectedCar(null);
        }
        Car selectedCar = this.frame.getFilteredCars().get(index);
        this.frame.setSelectedCar(selectedCar);

        this.carNameTextField.setText(selectedCar.getName());
        this.carColorTextField.setText(selectedCar.getColor());
        this.carDescriptionTextField.setText(selectedCar.getDescription());

        double dailyRateDecimal = (double) selectedCar.getDailyRate() / 100;
        String dailyRateString = String.format("RS %.2f", dailyRateDecimal);
        this.carDailyRateTextField.setText(dailyRateString);

        this.carBrandTextField.setText(selectedCar.getBrand().getName());
        this.carCategoryTextField.setText(selectedCar.getCategory().getName());

        String[] specificationNames = new String[this.frame.getSelectedCar().getSpecifications()
                .size()];
        for (int i = 0; i < specificationNames.length; i++) {
            specificationNames[i] = this.frame.getSelectedCar().getSpecifications().get(i)
                    .getName();
        }
        this.carSpecificationsJList.setListData(specificationNames);

        ImageIcon carImageIcon = this.daoFactory.getCarDao().getImageIconByCar(selectedCar, 150,
                150);
        this.carImageLabel.setIcon(carImageIcon);

    }

}

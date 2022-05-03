package com.juliano.carrental.gui.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.GregorianCalendar;

import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.gui.CreateRentalFrame;

public class RentalDatePickersPropertyChangeListener implements PropertyChangeListener {
    private CreateRentalFrame frame;
    private DaoFactory daoFactory;
    private JDatePicker startDatePicker;
    private JDatePicker endDatePicker;
    private JTextField rentalTotalValueTextField;

    public RentalDatePickersPropertyChangeListener(CreateRentalFrame frame, DaoFactory daoFactory,
            JDatePicker startDatePicker, JDatePicker endDatePicker,
            JTextField rentalTotalValueTextField) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.startDatePicker = startDatePicker;
        this.endDatePicker = endDatePicker;
        this.rentalTotalValueTextField = rentalTotalValueTextField;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (this.startDatePicker == null || this.endDatePicker == null) {
            return;
        }
        if (this.frame.getSelectedCar() == null) {
            return;
        }

        GregorianCalendar startCalendar = (GregorianCalendar) this.startDatePicker
                .getModel().getValue();
        if (startCalendar == null) {
            return;
        }

        GregorianCalendar endCalendar = (GregorianCalendar) this.endDatePicker.getModel()
                .getValue();
        if (endCalendar == null) {
            return;
        }

//        long diffInMilis = endCalendar.getTimeInMillis()
//                - startCalendar.getTimeInMillis();
//        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMilis);
//        if (days <= 0) {
//            return;
//        }

        int rentalTotalValue = this.daoFactory.getRentalDao()
                .getTotalRentalValueByCalendars(this.frame.getSelectedCar(), startCalendar, endCalendar);

        double rentalTotalValueDecimal = rentalTotalValue / 100;
        String rentalTotalValueString = String.format("R$ %.2f",
                rentalTotalValueDecimal);
        this.rentalTotalValueTextField.setText(rentalTotalValueString);

    }

}

package com.juliano.carrental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Rental;

public class CreateRentalBtnActionListener implements ActionListener {
    private CreateRentalFrame frame;
    private DaoFactory daoFactory;
    private JDatePicker startDatePicker;
    private JDatePicker endDatePicker;
    private JTextField rentalTotalTextField;
    private JTable availableCarsTable;

    public CreateRentalBtnActionListener(CreateRentalFrame frame, DaoFactory daoFactory,
            JDatePicker startDatePicker, JDatePicker endDatePicker,
            JTextField rentalTotalTextField, JTable availableCarsTable) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.startDatePicker = startDatePicker;
        this.endDatePicker = endDatePicker;
        this.rentalTotalTextField = rentalTotalTextField;
        this.availableCarsTable = availableCarsTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.frame.getSelectedCustomer() == null) {
            JOptionPane.showMessageDialog(this.frame, "Selecione um cliente!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.frame.getSelectedCar() == null) {
            JOptionPane.showMessageDialog(this.frame, "Selecione um carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        GregorianCalendar startCalendar = (GregorianCalendar) startDatePicker.getModel().getValue();
        if (startCalendar == null) {
            JOptionPane.showMessageDialog(this.frame, "Selecione a data de início!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        GregorianCalendar endCalendar = (GregorianCalendar) endDatePicker.getModel().getValue();
        if (endCalendar == null) {
            JOptionPane.showMessageDialog(this.frame, "Selecione a data de entrega!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

//        long diffInMilis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
//        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMilis);

//        if (days <= 0) {
//            JOptionPane.showMessageDialog(this.frame, "O tempo mínimo de locação é de um dia!",
//                    "ERRO",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }

        int rentalTotalValue = this.daoFactory.getRentalDao()
                .getTotalRentalValueByCalendars(this.frame.getSelectedCar(), startCalendar, endCalendar);
        if (rentalTotalValue == -1) {
            JOptionPane.showMessageDialog(this.frame, "O tempo mínimo de locação é de um dia!",
                    "ERRO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Rental rental = new Rental();
        rental.setCar(this.frame.getSelectedCar());
        rental.setCustomer(this.frame.getSelectedCustomer());
        rental.setStartDate(new Date(startCalendar.getTimeInMillis()));
        rental.setEndDate(new Date(endCalendar.getTimeInMillis()));
        rental.setTotal(rentalTotalValue);
        if (!daoFactory.getRentalDao().save(rental)) {
            JOptionPane.showMessageDialog(this.frame, "Não foi possível realizar o cadastro!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(this.frame, "Carro alugado com sucesso!",
                "SUCESSO",
                JOptionPane.PLAIN_MESSAGE);
        this.frame.clearCarFields();

        this.frame.setAvailableCars(this.daoFactory.getCarDao().getAvailableCars());
    }

}

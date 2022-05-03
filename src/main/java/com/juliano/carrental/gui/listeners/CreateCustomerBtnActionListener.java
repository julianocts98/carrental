package com.juliano.carrental.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Customer;

public class CreateCustomerBtnActionListener implements ActionListener {
    private JFrame frame;
    private DaoFactory daoFactory;
    private JTextField nameTextField;
    private JDatePicker birthDatePicker;
    private JTextField emailTextField;
    private JTextField driverLicenseTextField;
    private JTextField addressTextField;
    private JTextField phoneNumberTextField;

    public CreateCustomerBtnActionListener(JFrame frame, DaoFactory daoFactory,
            JTextField nameTextField, JDatePicker birthDatePicker, JTextField emailTextField,
            JTextField driverLicenseTextField, JTextField addressTextField,
            JTextField phoneNumberTextField) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.nameTextField = nameTextField;
        this.birthDatePicker = birthDatePicker;
        this.emailTextField = emailTextField;
        this.driverLicenseTextField = driverLicenseTextField;
        this.addressTextField = addressTextField;
        this.phoneNumberTextField = phoneNumberTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Customer customer = new Customer();
        customer.setName(this.nameTextField.getText());

        GregorianCalendar calendar = (GregorianCalendar) birthDatePicker.getModel().getValue();
        Date date = new Date(calendar.getTimeInMillis());
        customer.setBirthDate(date);

        customer.setEmail(this.emailTextField.getText());
        customer.setDriverLicense(this.driverLicenseTextField.getText());
        customer.setAddress(this.addressTextField.getText());
        customer.setPhoneNumber(this.phoneNumberTextField.getText());

        this.daoFactory.getCustomerDao().save(customer);

    }

}

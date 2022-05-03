package com.juliano.carrental.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        if (this.nameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo do nome!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.nameTextField.grabFocus();
            return;
        }

        if (this.birthDatePicker.getModel().getValue() == null) {
            JOptionPane.showMessageDialog(this.frame, "Selecione uma data de aniversário!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.emailTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo do email!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.emailTextField.grabFocus();
            return;
        }

        if (this.driverLicenseTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo da CNH!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.driverLicenseTextField.grabFocus();
            return;
        }
        if (this.addressTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo do endereço!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.addressTextField.grabFocus();
            return;
        }
        if (this.phoneNumberTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo do telefone!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.phoneNumberTextField.grabFocus();
            return;
        }

        Customer customer = new Customer();
        customer.setName(this.nameTextField.getText());

        GregorianCalendar calendar = (GregorianCalendar) birthDatePicker.getModel().getValue();
        Date date = new Date(calendar.getTimeInMillis());
        customer.setBirthDate(date);

        customer.setEmail(this.emailTextField.getText());
        customer.setDriverLicense(this.driverLicenseTextField.getText());
        customer.setAddress(this.addressTextField.getText());
        customer.setPhoneNumber(this.phoneNumberTextField.getText());

        if (!this.daoFactory.getCustomerDao().save(customer)) {
            JOptionPane.showMessageDialog(this.frame, "O usuário não pôde ser cadastrado!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this.frame, "Usuário cadastrado com sucesso!", "SUCESSO",
                JOptionPane.PLAIN_MESSAGE);

    }

}

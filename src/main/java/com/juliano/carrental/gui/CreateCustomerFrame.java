package com.juliano.carrental.gui;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.gui.listeners.CreateCustomerBtnActionListener;

public class CreateCustomerFrame extends JFrame {
    private static final long serialVersionUID = 9137318202706842012L;
    private DaoFactory daoFactory;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField driverLicenseTextField;
    private JTextField addressTextField;
    private JTextField phoneNumberTextField;
    private JButton createCustomerBtn;
    private JLabel lblNewLabel_4;
    private JTextField emailTextField;

    /**
     * Create the frame.
     */
    public CreateCustomerFrame(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNome = new JLabel("Nome:");

        JLabel lblNewLabel = new JLabel("Data de nascimento:");

        JLabel lblNewLabel_1 = new JLabel("CNH:");

        JLabel lblNewLabel_2 = new JLabel("Endereço:");

        JLabel lblNewLabel_3 = new JLabel("Telefone:");

        nameTextField = new JTextField();
        nameTextField.setColumns(10);

        driverLicenseTextField = new JTextField();
        driverLicenseTextField.setColumns(10);

        addressTextField = new JTextField();
        addressTextField.setColumns(10);

        phoneNumberTextField = new JTextField();
        phoneNumberTextField.setColumns(10);

        lblNewLabel_4 = new JLabel("Email:");

        JDatePicker birthDatePicker = new JDatePicker();

        emailTextField = new JTextField();
        emailTextField.setColumns(10);

        createCustomerBtn = new JButton("Cadastrar");
        createCustomerBtn
                .addActionListener(new CreateCustomerBtnActionListener(this, daoFactory,
                        nameTextField, birthDatePicker, emailTextField,
                        driverLicenseTextField, addressTextField,
                        phoneNumberTextField));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                .addGap(30)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(lblNewLabel_3)
                                        .addComponent(lblNome)
                                        .addComponent(lblNewLabel)
                                        .addComponent(lblNewLabel_4))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(birthDatePicker, GroupLayout.DEFAULT_SIZE,
                                                280,
                                                Short.MAX_VALUE)
                                        .addComponent(emailTextField, GroupLayout.DEFAULT_SIZE, 280,
                                                Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addComponent(addressTextField, 280, 280,
                                                                280)
                                                        .addComponent(phoneNumberTextField, 280,
                                                                280, 280)
                                                        .addComponent(createCustomerBtn,
                                                                Alignment.TRAILING))
                                                .addPreferredGap(ComponentPlacement.RELATED,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(nameTextField, GroupLayout.DEFAULT_SIZE, 280,
                                                Short.MAX_VALUE)
                                        .addComponent(driverLicenseTextField,
                                                GroupLayout.DEFAULT_SIZE, 280,
                                                Short.MAX_VALUE))
                                .addGap(89)));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(37)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNome))
                                .addGap(10)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel)
                                        .addComponent(birthDatePicker, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_4)
                                        .addComponent(emailTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(driverLicenseTextField,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(addressTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_3)
                                        .addComponent(phoneNumberTextField,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(32)
                                .addComponent(createCustomerBtn)
                                .addContainerGap(42, Short.MAX_VALUE)));
        contentPane.setLayout(gl_contentPane);
    }
}

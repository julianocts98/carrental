package com.juliano.carrental.gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.gui.listeners.CreateCategoryBtnActionListener;

public class CreateCategoryFrame extends JFrame {

    // TODO Search about serialVersionUID
    private static final long serialVersionUID = 2565753404184632641L;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField descriptionTextField;

    /**
     * Create the frame.
     */
    public CreateCategoryFrame(DaoFactory daoFactory) {
        setTitle("Cadastro de Categoria");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Nome:");

        JLabel lblNewLabel_1 = new JLabel("Descrição:");

        nameTextField = new JTextField();
        nameTextField.setColumns(10);

        descriptionTextField = new JTextField();
        descriptionTextField.setColumns(10);

        JButton createCategoryBtn = new JButton("Cadastrar");

        createCategoryBtn.addActionListener(
                new CreateCategoryBtnActionListener(this, daoFactory, nameTextField,
                        descriptionTextField));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(25)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel, Alignment.TRAILING)
                                        .addComponent(lblNewLabel_1, Alignment.TRAILING))
                                .addGap(35)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(nameTextField, Alignment.TRAILING,
                                                GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                        .addComponent(descriptionTextField,
                                                GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                                .addGap(92))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(254, Short.MAX_VALUE)
                                .addComponent(createCategoryBtn, GroupLayout.PREFERRED_SIZE, 124,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(58)));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(44, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(32)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(descriptionTextField,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(50)
                                .addComponent(createCategoryBtn)
                                .addGap(65)));
        contentPane.setLayout(gl_contentPane);
    }
}

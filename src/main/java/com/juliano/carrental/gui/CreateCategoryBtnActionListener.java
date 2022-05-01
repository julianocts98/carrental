package com.juliano.carrental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Category;

public class CreateCategoryBtnActionListener implements ActionListener {
    private final DaoFactory daoFactory;
    private final JTextField nameTextField;
    private final JTextField descriptionTextField;
    private final JFrame frame;

    public CreateCategoryBtnActionListener(JFrame frame, DaoFactory daoFactory,
            JTextField nameTextField,
            JTextField descriptionTextField) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.nameTextField = nameTextField;
        this.descriptionTextField = descriptionTextField;
    }

    private void clearTextFields() {
        this.nameTextField.setText("");
        this.descriptionTextField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Category category = new Category();
        category.setName(this.nameTextField.getText());
        category.setDescription(this.descriptionTextField.getText());

        if (!daoFactory.getCategoryDao().save(category)) {
            String errMessage = String.format("A categoria %s já existe!",
                    this.nameTextField.getText());
            JOptionPane.showMessageDialog(this.frame, errMessage, "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            nameTextField.grabFocus();
            return;
        }
        String successMessage = String.format("A categoria %s foi cadastrada com sucesso!",
                this.nameTextField.getText());
        JOptionPane.showMessageDialog(this.frame, successMessage, "SUCESSO",
                JOptionPane.PLAIN_MESSAGE);
        this.clearTextFields();
        nameTextField.grabFocus();
    }
}

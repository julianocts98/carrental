package com.juliano.carrental.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Specification;

public class CreateSpecificationBtnActionListener implements ActionListener {
    private final DaoFactory daoFactory;
    private final JTextField nameTextField;
    private final JTextField descriptionTextField;
    private final JFrame frame;

    public CreateSpecificationBtnActionListener(JFrame frame, DaoFactory daoFactory,
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
        if (this.nameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo do nome!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.nameTextField.grabFocus();
            return;
        }
        if (this.descriptionTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frame, "Preencha o campo da descrição!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.descriptionTextField.grabFocus();
            return;
        }

        Specification specification = new Specification();
        specification.setName(this.nameTextField.getText());
        specification.setDescription(this.descriptionTextField.getText());

        if (!daoFactory.getSpecificationDao().save(specification)) {
            String errMessage = String.format("A especificação %s já existe!",
                    this.nameTextField.getText());
            JOptionPane.showMessageDialog(this.frame, errMessage, "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            nameTextField.grabFocus();
            return;
        }
        String successMessage = "A especificação foi cadastrada com sucesso!";
        JOptionPane.showMessageDialog(this.frame, successMessage, "SUCESSO",
                JOptionPane.PLAIN_MESSAGE);
        this.clearTextFields();
        nameTextField.grabFocus();
    }
}

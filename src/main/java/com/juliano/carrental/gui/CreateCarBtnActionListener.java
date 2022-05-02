package com.juliano.carrental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Category;
import com.juliano.carrental.model.Specification;

public class CreateCarBtnActionListener implements ActionListener {
    private JFrame frame;
    private DaoFactory daoFactory;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> brandComboBox;
    private JList<String> specificationJList;
    private JTextField dailyRateTextField;
    private JTextField licensePlateTextField;
    private JTextField colorTextField;
    private JCheckBox availableCheckBox;
    private String imgPath;
    private ArrayList<Specification> specifications;

    public CreateCarBtnActionListener(JFrame frame, DaoFactory daoFactory, JTextField nameTextField,
            JTextField descriptionTextField,
            JComboBox<String> categoryComboBox, JComboBox<String> brandComboBox,
            JList<String> specificationList, JTextField dailyRateTextField,
            JTextField licensePlateTextField, JTextField colorTextField,
            JCheckBox availableCheckBox, ArrayList<Specification> specifications) {
        this.frame = frame;
        this.daoFactory = daoFactory;
        this.nameTextField = nameTextField;
        this.descriptionTextField = descriptionTextField;
        this.categoryComboBox = categoryComboBox;
        this.brandComboBox = brandComboBox;
        this.specificationJList = specificationList;
        this.dailyRateTextField = dailyRateTextField;
        this.licensePlateTextField = licensePlateTextField;
        this.colorTextField = colorTextField;
        this.availableCheckBox = availableCheckBox;
        this.specifications = specifications;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (this.nameTextField.getText() == "") {
            JOptionPane.showMessageDialog(this.frame, "Digite o nome do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.nameTextField.grabFocus();
            return;
        }
        if (this.descriptionTextField.getText() == "") {
            JOptionPane.showMessageDialog(this.frame, "Digite a descrição do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.descriptionTextField.grabFocus();
            return;
        }
        if (this.brandComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this.frame, "Selecione a marca do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.categoryComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this.frame, "Selecione a categoria do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.specificationJList.getSelectedValuesList().size() == 0) {
            JOptionPane.showMessageDialog(this.frame, "Selecione as especificações do carro!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.dailyRateTextField.getText() == "") {
            JOptionPane.showMessageDialog(this.frame,
                    "Digite a o valor do aluguel diário do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.dailyRateTextField.grabFocus();
            return;
        }
        if (!this.imgPath.endsWith("png") && !this.imgPath.endsWith("jpg")
                && !this.imgPath.endsWith("jpeg")) {
            JOptionPane.showMessageDialog(this.frame,
                    "O formato da imagem selecionada não é aceito! Insira somente JPG/JPEG ou PNG",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.licensePlateTextField.getText() == "") {
            JOptionPane.showMessageDialog(this.frame, "Digite a placa do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.licensePlateTextField.grabFocus();
            return;
        }
        if (this.colorTextField.getText() == "") {
            JOptionPane.showMessageDialog(this.frame, "Digite a cor do carro!", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            this.colorTextField.grabFocus();
            return;
        }

        Car car = new Car();
        car.setName(this.nameTextField.getText());
        car.setDescription(this.descriptionTextField.getText());

        String brandName = (String) this.brandComboBox.getSelectedItem();
        Brand brand = this.daoFactory.getBrandDao().getByName(brandName);
        car.setBrand(brand);

        String categoryName = (String) this.categoryComboBox.getSelectedItem();
        Category category = this.daoFactory.getCategoryDao().getByName(categoryName);
        car.setCategory(category);

        ArrayList<Specification> carSpecifications = new ArrayList<Specification>(
                specificationJList.getSelectedValuesList().size());

        for (int i : specificationJList.getSelectedIndices()) {
            carSpecifications.add(specifications.get(i));
        }
        car.setSpecifications(carSpecifications);

        double dailyRateDecimals = Double.parseDouble(this.dailyRateTextField.getText());
        int dailyRateInCents = (int) Math.round(dailyRateDecimals * 100);
        car.setDailyRate(dailyRateInCents);

        File file = new File(this.imgPath);
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
            car.setImage(fis.readAllBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        car.setLicensePlate(this.licensePlateTextField.getText());
        car.setColor(this.colorTextField.getText());
        car.setAvailable(availableCheckBox.isSelected());

        daoFactory.getCarDao().save(car);

    }

}

package com.juliano.carrental.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.gui.listeners.CreateCarBtnActionListener;
import com.juliano.carrental.gui.listeners.LoadImageBtnActionListener;
import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Category;
import com.juliano.carrental.model.Specification;

public class CreateCarFrame extends JFrame {
    private DaoFactory daoFactory;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField dailyRateTextField;
    private JTextField licensePlateTextField;
    private JTextField colorTextField;

    /**
     * Create the frame.
     */
    public CreateCarFrame(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        setMinimumSize(new Dimension(600, 500));
        setTitle("Cadastro de Carro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 517, 420);
        contentPane = new JPanel();
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Nome:");

        JLabel lblNewLabel_1 = new JLabel("Descrição:");

        JLabel lblNewLabel_2 = new JLabel("Categoria:");
        lblNewLabel_2.setToolTipText("Marca");

        JLabel lblNewLabel_3 = new JLabel("Especificações:");

        JLabel lblNewLabel_4 = new JLabel("Taxa de aluguel/dia:");

        JLabel lblNewLabel_6 = new JLabel("Imagem");

        JLabel lblNewLabel_7 = new JLabel("Cor:");

        JLabel lblNewLabel_8 = new JLabel("Placa:");

        JLabel lblMarca = new JLabel("Marca:");

        nameTextField = new JTextField();
        nameTextField.setColumns(10);

        descriptionTextField = new JTextField();
        descriptionTextField.setColumns(10);

        dailyRateTextField = new JTextField();
        dailyRateTextField.setColumns(10);

        ArrayList<Brand> brands = this.daoFactory.getBrandDao().getAll();
        String[] brandsNames = new String[brands.size() + 1];
        brandsNames[0] = "<SELECIONE UMA MARCA>";
        for (int i = 0; i < brands.size(); i++) {
            brandsNames[i + 1] = brands.get(i).getName();
        }

        JComboBox brandComboBox = new JComboBox(brandsNames);

        ArrayList<Category> categories = this.daoFactory.getCategoryDao().getAll();
        String[] categoriesNames = new String[categories.size() + 1];
        categoriesNames[0] = "<SELECIONE UMA CATEGORIA>";
        for (int i = 0; i < categories.size(); i++) {
            categoriesNames[i + 1] = categories.get(i).getName();
        }

        JComboBox categoryComboBox = new JComboBox(categoriesNames);

        ArrayList<Specification> specifications = this.daoFactory.getSpecificationDao().getAll();
        String[] specificationsNames = new String[specifications.size()];
        for (int i = 0; i < specifications.size(); i++) {
            specificationsNames[i] = specifications.get(i).getName();
        }

        JList specificationList = new JList(specificationsNames);

        licensePlateTextField = new JTextField();
        licensePlateTextField.setColumns(10);

        colorTextField = new JTextField();
        colorTextField.setColumns(10);

        JCheckBox availableCheckbox = new JCheckBox("Disponível");

        JButton createCarBtn = new JButton("Cadastrar");

        // TODO HIGH COUPLING ALERT!!
        CreateCarBtnActionListener createCarBtnActionListener = new CreateCarBtnActionListener(this,
                daoFactory, nameTextField,
                descriptionTextField, categoryComboBox, brandComboBox,
                specificationList, dailyRateTextField, licensePlateTextField,
                colorTextField, availableCheckbox, specifications);
        createCarBtn.addActionListener(createCarBtnActionListener);

        JScrollPane listScrollPane = new JScrollPane();

        JButton loadImageBtn = new JButton("Carregar Imagem");
        JLabel imgLabel = new JLabel("");

        // PASSING ANOTHER ACTION LISTENER AS A ARGUMENT TO A NEW ONE, ONLY TO BE ABLE
        // TO DEAL
        // WITH IMG PATHS
        loadImageBtn.addActionListener(
                new LoadImageBtnActionListener(this, imgLabel, createCarBtnActionListener));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblNewLabel_6)
                                                .addGap(80))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane
                                                                .createSequentialGroup()
                                                                .addGroup(gl_contentPane
                                                                        .createParallelGroup(
                                                                                Alignment.LEADING)
                                                                        .addComponent(lblNewLabel)
                                                                        .addComponent(lblNewLabel_1)
                                                                        .addComponent(lblMarca))
                                                                .addGap(41)
                                                                .addGroup(gl_contentPane
                                                                        .createParallelGroup(
                                                                                Alignment.LEADING)
                                                                        .addComponent(brandComboBox,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                255,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(nameTextField,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                255,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(
                                                                                descriptionTextField,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                255,
                                                                                GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_contentPane
                                                                .createSequentialGroup()
                                                                .addComponent(lblNewLabel_4,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        134, Short.MAX_VALUE)
                                                                .addGap(222))
                                                        .addGroup(gl_contentPane
                                                                .createParallelGroup(
                                                                        Alignment.TRAILING, false)
                                                                .addGroup(Alignment.LEADING,
                                                                        gl_contentPane
                                                                                .createSequentialGroup()
                                                                                .addGap(146)
                                                                                .addComponent(
                                                                                        dailyRateTextField))
                                                                .addGroup(Alignment.LEADING,
                                                                        gl_contentPane
                                                                                .createSequentialGroup()
                                                                                .addGroup(
                                                                                        gl_contentPane
                                                                                                .createParallelGroup(
                                                                                                        Alignment.LEADING)
                                                                                                .addComponent(
                                                                                                        lblNewLabel_2)
                                                                                                .addComponent(
                                                                                                        lblNewLabel_3))
                                                                                .addPreferredGap(
                                                                                        ComponentPlacement.RELATED)
                                                                                .addGroup(
                                                                                        gl_contentPane
                                                                                                .createParallelGroup(
                                                                                                        Alignment.LEADING,
                                                                                                        false)
                                                                                                .addComponent(
                                                                                                        listScrollPane,
                                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                                        255,
                                                                                                        GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(
                                                                                                        categoryComboBox,
                                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                                        255,
                                                                                                        GroupLayout.PREFERRED_SIZE)))))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(gl_contentPane
                                                                .createSequentialGroup()
                                                                .addGroup(gl_contentPane
                                                                        .createParallelGroup(
                                                                                Alignment.TRAILING)
                                                                        .addComponent(lblNewLabel_8)
                                                                        .addComponent(
                                                                                lblNewLabel_7))
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addGroup(gl_contentPane
                                                                        .createParallelGroup(
                                                                                Alignment.LEADING,
                                                                                false)
                                                                        .addComponent(
                                                                                colorTextField,
                                                                                Alignment.TRAILING)
                                                                        .addComponent(
                                                                                licensePlateTextField,
                                                                                Alignment.TRAILING,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                133,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(createCarBtn,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                119,
                                                                                GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_contentPane
                                                                .createParallelGroup(
                                                                        Alignment.LEADING)
                                                                .addComponent(imgLabel,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        134,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(loadImageBtn)))
                                                .addGap(26))))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(457)
                                .addComponent(availableCheckbox)
                                .addGap(50)));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblNewLabel_6)
                                                .addGap(18)
                                                .addComponent(imgLabel)
                                                .addGap(18)
                                                .addComponent(loadImageBtn)
                                                .addGap(27)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(licensePlateTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNewLabel_8))
                                                .addGap(18)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_7)
                                                        .addComponent(colorTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(18)
                                                .addComponent(availableCheckbox))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(33)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel)
                                                        .addComponent(nameTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(18)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(descriptionTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(18)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblMarca)
                                                        .addComponent(brandComboBox,
                                                                GroupLayout.PREFERRED_SIZE, 25,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(27)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_2)
                                                        .addComponent(categoryComboBox,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_contentPane
                                                                .createSequentialGroup()
                                                                .addGap(47)
                                                                .addComponent(lblNewLabel_3))
                                                        .addGroup(gl_contentPane
                                                                .createSequentialGroup()
                                                                .addGap(18)
                                                                .addComponent(listScrollPane,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        84,
                                                                        GroupLayout.PREFERRED_SIZE)))
                                                .addGap(35)
                                                .addGroup(gl_contentPane
                                                        .createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(lblNewLabel_4)
                                                        .addComponent(dailyRateTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(createCarBtn)
                                .addGap(33)));

        listScrollPane.setViewportView(specificationList);
        contentPane.setLayout(gl_contentPane);

    }
}

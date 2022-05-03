package com.juliano.carrental.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;

import com.juliano.carrental.dao.DaoFactory;
import com.juliano.carrental.dao.FilterBtnActionListener;
import com.juliano.carrental.gui.listeners.AvailableCarsTableMouseAdapter;
import com.juliano.carrental.gui.listeners.CreateRentalBtnActionListener;
import com.juliano.carrental.gui.listeners.RentalDatePickersPropertyChangeListener;
import com.juliano.carrental.model.Brand;
import com.juliano.carrental.model.Car;
import com.juliano.carrental.model.Category;
import com.juliano.carrental.model.Customer;

public class CreateRentalFrame extends JFrame {
    private DaoFactory daoFactory;
    private ArrayList<Car> availableCars;
    private ArrayList<Car> filteredCars;
    private Customer selectedCustomer;
    private Car selectedCar;

    private JPanel contentPane;
    private JTextField carNameTextField;
    private JTextField carDescriptionTextField;
    private JTextField carColorTextField;
    private JTextField carDailyRateTextField;
    private JTextField carBrandTextField;
    private JTextField carCategoryTextField;
    private JTextField rentalTotalValueTextField;
    private JTable availableCarsTable;
    private JList carSpecificationsJList;
    private JLabel carImageLabel;
    private JDatePicker startDatePicker;
    private JDatePicker endDatePicker;

    public ArrayList<Car> getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(ArrayList<Car> availableCars) {
        this.availableCars = availableCars;
    }

    public ArrayList<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(ArrayList<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    /**
     * Create the frame.
     */
    public CreateRentalFrame(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;

        setMinimumSize(new Dimension(650, 750));
        setTitle("Cadastro de Locação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        ArrayList<Customer> customers = this.daoFactory.getCustomerDao().getAll();
        String[] customersNames = new String[customers.size() + 1];
        customersNames[0] = "<SELECIONE UM CLIENTE>";
        for (int i = 0; i < customers.size(); i++) {
            customersNames[i + 1] = customers.get(i).getName();
        }

        JComboBox customerComboBox = new JComboBox(customersNames);
        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = customerComboBox.getSelectedIndex();
                if (selectedIndex == 0 || selectedIndex == -1) {
                    setSelectedCustomer(null);
                    return;
                }
                int customerIndex = customerComboBox.getSelectedIndex() - 1;
                setSelectedCustomer(customers.get(customerIndex));
            }
        });

        JLabel lblNewLabel = new JLabel("Cliente:");

        JLabel lblNewLabel_1 = new JLabel("Marca:");

        ArrayList<Brand> brands = this.daoFactory.getBrandDao().getAll();
        String[] brandsNames = new String[brands.size() + 1];
        brandsNames[0] = "<MARCA>";
        for (int i = 0; i < brands.size(); i++) {
            brandsNames[i + 1] = brands.get(i).getName();
        }

        JComboBox brandComboBox = new JComboBox(brandsNames);

        JLabel lblNewLabel_2 = new JLabel("Categoria");

        ArrayList<Category> categories = this.daoFactory.getCategoryDao().getAll();
        String[] categoriesNames = new String[categories.size() + 1];
        categoriesNames[0] = "<CATEGORIA>";
        for (int i = 0; i < categories.size(); i++) {
            categoriesNames[i + 1] = categories.get(i).getName();
        }

        JComboBox categoryComboBox = new JComboBox(categoriesNames);

        carNameTextField = new JTextField();
        carNameTextField.setEditable(false);
        carNameTextField.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Descrição:");

        carDescriptionTextField = new JTextField();
        carDescriptionTextField.setEditable(false);
        carDescriptionTextField.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("Cor:");

        carColorTextField = new JTextField();
        carColorTextField.setEditable(false);
        carColorTextField.setColumns(10);

        JLabel lblNewLabel_8 = new JLabel("Taxa diária:");

        carDailyRateTextField = new JTextField();
        carDailyRateTextField.setEditable(false);
        carDailyRateTextField.setColumns(10);

        JLabel lblNewLabel_9 = new JLabel("Marca:");

        carBrandTextField = new JTextField();
        carBrandTextField.setEditable(false);
        carBrandTextField.setColumns(10);

        JLabel lblNewLabel_10 = new JLabel("Categoria:");

        carCategoryTextField = new JTextField();
        carCategoryTextField.setEditable(false);
        carCategoryTextField.setColumns(10);

        carSpecificationsJList = new JList();
        carSpecificationsJList.setVisibleRowCount(-1);
        carSpecificationsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        setAvailableCars(this.daoFactory.getCarDao().getAvailableCars());
        setFilteredCars(this.getAvailableCars());

        carImageLabel = new JLabel();

//        File file = new File("/home/juliano/Desktop/veloster.jpg");
//        FileInputStream fis;
//        try {
//            fis = new FileInputStream(file);
//            ByteArrayInputStream bais = new ByteArrayInputStream(fis.readAllBytes());
//            Image image = ImageIO.read(bais).getScaledInstance(150, 150,
//                    java.awt.Image.SCALE_SMOOTH);
//            ImageIcon imgIcon = new ImageIcon(image);
//            carImageLabel.setIcon(imgIcon);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        availableCarsTable = new JTable();
        populateAvailableCarsTable(availableCarsTable, getAvailableCars());
        availableCarsTable.addMouseListener(
                new AvailableCarsTableMouseAdapter(this, daoFactory, availableCarsTable,
                        carNameTextField, carColorTextField, carDescriptionTextField,
                        carDailyRateTextField, carBrandTextField,
                        carCategoryTextField, carSpecificationsJList, carImageLabel,
                        rentalTotalValueTextField, endDatePicker));

        rentalTotalValueTextField = new JTextField();
        rentalTotalValueTextField.setEditable(false);
        rentalTotalValueTextField.setColumns(10);

        startDatePicker = new JDatePicker();
        startDatePicker.getFormattedTextField()
                .addPropertyChangeListener(
                        new RentalDatePickersPropertyChangeListener(this, daoFactory,
                                startDatePicker, endDatePicker, rentalTotalValueTextField));

        JLabel lblNewLabel_3 = new JLabel("Data de início:");

        JLabel lblNewLabel_4 = new JLabel("Data de entrega:");

        endDatePicker = new JDatePicker();
        endDatePicker.getFormattedTextField()
                .addPropertyChangeListener(
                        new RentalDatePickersPropertyChangeListener(this, daoFactory,
                                startDatePicker, endDatePicker, rentalTotalValueTextField));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel();

        JLabel lblNewLabel_11 = new JLabel("Valor total:");

        JButton filterBtn = new JButton("Filtrar");
        filterBtn.addActionListener(new FilterBtnActionListener(this, daoFactory,
                availableCarsTable, customerComboBox, brandComboBox, categoryComboBox,
                startDatePicker, endDatePicker, carNameTextField, carColorTextField,
                carDescriptionTextField, carDailyRateTextField, carBrandTextField,
                carCategoryTextField, carSpecificationsJList, carImageLabel,
                rentalTotalValueTextField));

        JButton createRentalBtn = new JButton("Cadastrar");
        createRentalBtn.addActionListener(new CreateRentalBtnActionListener(this, daoFactory,
                startDatePicker, endDatePicker, rentalTotalValueTextField, availableCarsTable));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(27, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane
                                                .createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(scrollPane,
                                                        GroupLayout.PREFERRED_SIZE, 563,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(panel, GroupLayout.PREFERRED_SIZE,
                                                        563, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addComponent(lblNewLabel_11)
                                                        .addPreferredGap(
                                                                ComponentPlacement.UNRELATED)
                                                        .addComponent(rentalTotalValueTextField,
                                                                GroupLayout.PREFERRED_SIZE, 165,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                        .addGroup(gl_contentPane
                                                                .createParallelGroup(
                                                                        Alignment.TRAILING)
                                                                .addComponent(lblNewLabel_1)
                                                                .addComponent(lblNewLabel_3)
                                                                .addComponent(lblNewLabel))
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addGroup(gl_contentPane
                                                                .createParallelGroup(
                                                                        Alignment.LEADING)
                                                                .addGroup(gl_contentPane
                                                                        .createSequentialGroup()
                                                                        .addGroup(gl_contentPane
                                                                                .createParallelGroup(
                                                                                        Alignment.LEADING)
                                                                                .addComponent(
                                                                                        brandComboBox,
                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                        175,
                                                                                        GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(
                                                                                        startDatePicker,
                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                        175,
                                                                                        GroupLayout.PREFERRED_SIZE))
                                                                        .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                        .addGroup(gl_contentPane
                                                                                .createParallelGroup(
                                                                                        Alignment.LEADING)
                                                                                .addGroup(
                                                                                        gl_contentPane
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(
                                                                                                        lblNewLabel_2)
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        categoryComboBox,
                                                                                                        0,
                                                                                                        203,
                                                                                                        Short.MAX_VALUE))
                                                                                .addGroup(
                                                                                        gl_contentPane
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(
                                                                                                        lblNewLabel_4)
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addGroup(
                                                                                                        gl_contentPane
                                                                                                                .createParallelGroup(
                                                                                                                        Alignment.LEADING)
                                                                                                                .addComponent(
                                                                                                                        filterBtn,
                                                                                                                        Alignment.TRAILING,
                                                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                                                        162,
                                                                                                                        Short.MAX_VALUE)
                                                                                                                .addComponent(
                                                                                                                        endDatePicker,
                                                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                                                        162,
                                                                                                                        Short.MAX_VALUE))))
                                                                        .addGap(52))
                                                                .addGroup(gl_contentPane
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                customerComboBox,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                460,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addContainerGap()))))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(createRentalBtn)
                                                .addGap(61)))));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(33)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(customerComboBox, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(brandComboBox, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(lblNewLabel_1))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel_4)
                                        .addComponent(endDatePicker, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(startDatePicker, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_3))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(filterBtn)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 274,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_11)
                                        .addComponent(rentalTotalValueTextField,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(createRentalBtn)
                                .addContainerGap(27, Short.MAX_VALUE)));

        scrollPane.setViewportView(availableCarsTable);

        JLabel lblNewLabel_5 = new JLabel("Nome:");

        JPanel carImagePanel = new JPanel();

        JScrollPane scrollPane_1 = new JScrollPane();
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 544,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_5,
                                                                Alignment.TRAILING)
                                                        .addComponent(lblNewLabel_10,
                                                                Alignment.TRAILING)
                                                        .addComponent(lblNewLabel_8,
                                                                Alignment.TRAILING)
                                                        .addComponent(lblNewLabel_6,
                                                                Alignment.TRAILING))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.LEADING,
                                                                false)
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                                .addComponent(carNameTextField,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        143,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addGap(34)
                                                                .addComponent(lblNewLabel_7)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.UNRELATED)
                                                                .addComponent(carColorTextField, 0,
                                                                        0,
                                                                        Short.MAX_VALUE))
                                                        .addComponent(carDescriptionTextField)
                                                        .addGroup(Alignment.TRAILING, gl_panel
                                                                .createSequentialGroup()
                                                                .addComponent(carDailyRateTextField,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        84,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.UNRELATED)
                                                                .addComponent(lblNewLabel_9)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.UNRELATED)
                                                                .addComponent(carBrandTextField))
                                                        .addComponent(carCategoryTextField,
                                                                GroupLayout.DEFAULT_SIZE, 297,
                                                                Short.MAX_VALUE))
                                                .addGap(18)
                                                .addComponent(carImagePanel,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap()));
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_5)
                                                        .addComponent(carNameTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNewLabel_7)
                                                        .addComponent(carColorTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(18)
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_6)
                                                        .addComponent(carDescriptionTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_8)
                                                        .addComponent(lblNewLabel_9)
                                                        .addComponent(carBrandTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(carDailyRateTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_panel
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(lblNewLabel_10)
                                                        .addComponent(carCategoryTextField,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(carImagePanel, GroupLayout.PREFERRED_SIZE,
                                                126,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 106,
                                        Short.MAX_VALUE)
                                .addContainerGap()));
        carImagePanel.setLayout(new BoxLayout(carImagePanel, BoxLayout.X_AXIS));

        carImagePanel.add(carImageLabel);

        scrollPane_1.setViewportView(carSpecificationsJList);
        panel.setLayout(gl_panel);
        contentPane.setLayout(gl_contentPane);

    }

    public void populateAvailableCarsTable(JTable availableCarsTable, ArrayList<Car> cars) {
        String[] columnNames = { "Nome", "Descrição", "R$/Dia" };
        Object[][] tableData = new Object[cars.size()][3];
        for (int i = 0; i < cars.size(); i++) {
            tableData[i][0] = cars.get(i).getName();
            tableData[i][1] = cars.get(i).getDescription();
            double dailyRateDecimal = (double) cars.get(i).getDailyRate() / 100;
            String dailyRateString = String.format("R$ %.2f", dailyRateDecimal);
            tableData[i][2] = dailyRateString;
        }
        DefaultTableModel table = new DefaultTableModel(tableData, columnNames);
        availableCarsTable.setModel(table);
    }

    public void clearCarFields() {
        carNameTextField.setText("");
        carColorTextField.setText("");
        carDescriptionTextField.setText("");
        carDailyRateTextField.setText("");
        this.carBrandTextField.setText("");
        this.carCategoryTextField.setText("");
        DefaultListModel emptyModel = new DefaultListModel();
        carSpecificationsJList.setModel(emptyModel);
        carImageLabel.setIcon(null);
        rentalTotalValueTextField.setText("");
    }

    public void showRentalValue() {
        GregorianCalendar startCalendar = (GregorianCalendar) startDatePicker.getModel().getValue();
        GregorianCalendar endCalendar = (GregorianCalendar) endDatePicker.getModel().getValue();

        long diffInMilis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMilis);

        if (days <= 0) {
            JOptionPane.showMessageDialog(this, "O tempo mínimo de locação é de um dia!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rentalTotalValue = this.getSelectedCar().getDailyRate() * days;
        double rentalTotalValueDecimal = rentalTotalValue / 100;
        String rentalTotalValueString = String.format("R$ %.2f", rentalTotalValueDecimal);
        this.rentalTotalValueTextField.setText(rentalTotalValueString);
    }

}

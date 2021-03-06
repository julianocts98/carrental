package com.juliano.carrental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.juliano.carrental.dao.DaoFactory;

public class MainFrame extends JFrame {
    private DaoFactory daoFactory;

    private CreateCustomerFrame createCustomerFrame;
    private CreateCategoryFrame createCategoryFrame;
    private CreateSpecificationFrame createSpecificationFrame;
    private CreateCarFrame createCarFrame;
    private CreateRentalFrame createRentalFrame;

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public MainFrame(DaoFactory daoFactory) {
        setTitle("Menu Principal");
        this.daoFactory = daoFactory;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu creationMenu = new JMenu("Cadastrar");
        menuBar.add(creationMenu);

        JMenuItem createCustomerMenu = new JMenuItem("Cadastrar Cliente");
        createCustomerMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createCustomerFrame == null || !createCustomerFrame.isDisplayable()) {
                    createCustomerFrame = new CreateCustomerFrame(daoFactory);
                    createCustomerFrame.setVisible(true);
                }
            }
        });
        creationMenu.add(createCustomerMenu);

        JMenuItem createCategoryMenu = new JMenuItem("Cadastrar Categoria");
        createCategoryMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createCategoryFrame == null || !createCategoryFrame.isDisplayable()) {
                    createCategoryFrame = new CreateCategoryFrame(daoFactory);
                    createCategoryFrame.setVisible(true);
                }

            }
        });
        creationMenu.add(createCategoryMenu);

        JMenuItem createSpecificationMenu = new JMenuItem("Cadastrar Especifica????o");
        createSpecificationMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createSpecificationFrame == null || !createSpecificationFrame.isDisplayable()) {
                    createSpecificationFrame = new CreateSpecificationFrame(daoFactory);
                    createSpecificationFrame.setVisible(true);
                }
            }
        });
        creationMenu.add(createSpecificationMenu);

        JMenuItem createCarMenu = new JMenuItem("Cadastrar Carro");
        createCarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createCarFrame == null || !createCarFrame.isDisplayable()) {
                    createCarFrame = new CreateCarFrame(daoFactory);
                    createCarFrame.setVisible(true);
                }
            }
        });
        creationMenu.add(createCarMenu);

        JMenuItem createRentalMenu = new JMenuItem("Cadastrar Aluguel");
        createRentalMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createRentalFrame == null || !createRentalFrame.isDisplayable()) {
                    createRentalFrame = new CreateRentalFrame(daoFactory);
                    createRentalFrame.setVisible(true);
                }
            }
        });
        creationMenu.add(createRentalMenu);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGap(0, 436, Short.MAX_VALUE));
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGap(0, 269, Short.MAX_VALUE));
        contentPane.setLayout(gl_contentPane);
    }
}
